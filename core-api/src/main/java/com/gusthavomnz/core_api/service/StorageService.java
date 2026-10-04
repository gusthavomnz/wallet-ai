package com.gusthavomnz.core_api.service;

import com.gusthavomnz.core_api.port.S3StoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final S3StoragePort s3StoragePort;

    public String upload(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        return s3StoragePort.uploadFile(file.getBytes(), fileName, file.getContentType());
    }
}
