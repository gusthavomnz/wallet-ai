package com.gusthavomnz.core_api.port;

public interface S3StoragePort {

    String uploadFile(byte[] fileData, String fileName, String contentType);

    String generateTempFileLink(String fileName, int expirationMinutes);

}
