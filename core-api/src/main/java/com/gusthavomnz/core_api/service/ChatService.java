package com.gusthavomnz.core_api.service;

import com.gusthavomnz.core_api.dto.chat.AiWorkerResponse;
import com.gusthavomnz.core_api.dto.chat.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final StorageService storageService;

    @Value("${ai.worker.url}")
    private String aiWorkerUrl;

    public ChatResponse sendMessage(String userMessage, MultipartFile file) throws IOException {
        String imageFileName = null;
        Map<String, Object> body = new HashMap<>();
        body.put("message", userMessage);

        if (file != null && !file.isEmpty()) {
            imageFileName = storageService.upload(file);
            body.put("imageBase64", Base64.getEncoder().encodeToString(file.getBytes()));
            body.put("mimeType", file.getContentType());
        }

        AiWorkerResponse aiResponse = RestClient.create(aiWorkerUrl)
                .post()
                .uri("/ai")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(AiWorkerResponse.class);

        return new ChatResponse(aiResponse.produto(), aiResponse.preco(), aiResponse.tag(), imageFileName);
    }
}
