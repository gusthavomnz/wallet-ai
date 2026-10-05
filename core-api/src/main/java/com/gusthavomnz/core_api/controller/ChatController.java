package com.gusthavomnz.core_api.controller;

import com.gusthavomnz.core_api.dto.chat.ChatResponse;
import com.gusthavomnz.core_api.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/message")
    public ChatResponse sendMessage(
            @RequestParam("message") String message,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        return chatService.sendMessage(message, file);
    }
}
