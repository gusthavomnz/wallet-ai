package com.gusthavomnz.core_api.infraestructure;

import com.gusthavomnz.core_api.port.S3StoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URI;
import java.time.Duration;
import java.net.URL;

@Component
public class S3StorageAdapter implements S3StoragePort {

    private final String publicUrl;
    private final String bucketName;
    private final S3Client s3Client;
    private final S3Presigner presigner;

    public S3StorageAdapter(
            @Value("${aws.s3.endpoint}") String endpoint,
            @Value("${aws.s3.public-url}") String publicUrl,
            @Value("${aws.bucket-name}") String bucketName,
            @Value("${aws.region}") String region,
            @Value("${aws.s3.access-key}") String accessKey,
            @Value("${aws.s3.secret-key}") String secretKey
    ) {
        this.publicUrl = publicUrl;
        this.bucketName = bucketName;

        /*
        Por estarmos utilizando o AWS mockado pelo Docker, não conseguimos utilizar o S3Client autogerenciado pela biblioteca da AWS.
        Sendo necessario a config inicial gerenciada manualmente
         */
        StaticCredentialsProvider credentials = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey)
        );

        S3Configuration s3Config = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build();

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(credentials)
                .serviceConfiguration(s3Config)
                .build();

        this.presigner = S3Presigner.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(credentials)
                .serviceConfiguration(s3Config)
                .build();

        initBucket();
    }

    /*
      Essa função é necessaria para inicializarmos o bucket na primeira vez que a aplicação é criada.
      Nesse mock do s3 não temos UI para fazermos isso pela interface
    */
    private void initBucket() {
        try {
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
        } catch (NoSuchBucketException e) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
        }
    }

    @Override
    public String uploadFile(byte[] fileData, String fileName, String contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(contentType)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileData));

        return generateTempFileLink(fileName,1);
    }


    public String generateTempFileLink(String fileName, int expirationMinutes) {
        GetObjectRequest objectRequest = GetObjectRequest.builder().
                bucket(this.bucketName).
                key(fileName).
                build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(expirationMinutes))
                .getObjectRequest(objectRequest)
                .build();

        URL signedUrl = presigner.presignGetObject(presignRequest).url();

        return signedUrl.toString().replace(
                signedUrl.getProtocol() + "://" + signedUrl.getHost() + (signedUrl.getPort() != -1 ? ":" + signedUrl.getPort() : ""),
                publicUrl
        );
    }

}
