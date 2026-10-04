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
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URI;

@Component
public class S3StorageAdapter implements S3StoragePort {

    private final String endpoint;

    private final String publicUrl;

    private final S3Client s3Client;

    private final String bucketName;

    private final String region;

    public S3StorageAdapter(
            @Value("${aws.s3.endpoint}") String endpoint,
            @Value("${aws.s3.public-url}") String publicUrl,
            @Value("${aws.bucket-name}") String bucketName,
            @Value("${aws.region}") String region,
            @Value("${aws.s3.access-key}") String accessKey,
            @Value("${aws.s3.secret-key}") String secretKey
    ) {
        this.endpoint = endpoint;
        this.publicUrl = publicUrl;
        this.bucketName = bucketName;
        this.region = region;
        /*
        Por estarmos utilizando o AWS mockado pelo Docker, não conseguimos utilizar o S3Client autogerenciado pela biblioteca da AWS.
        Sendo necessario a config inicial gerenciada manualmente
         */

        this.s3Client = S3Client.builder()
                .region(Region.of(this.region))
                .endpointOverride(URI.create(this.endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
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

        return String.format("%s/%s/%s", publicUrl, bucketName, fileName);
    }
}