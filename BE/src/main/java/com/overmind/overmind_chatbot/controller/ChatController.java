package com.overmind.overmind_chatbot.controller;

import com.overmind.overmind_chatbot.dto.ChatMessageDto;
import com.overmind.overmind_chatbot.entity.ChatMessage;
import com.overmind.overmind_chatbot.service.ChatService;
import com.overmind.overmind_chatbot.entity.enums.MessageType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private ModelMapper modelMapper;

    // 대화 메시지 저장 (사용자가 보낸 메시지)
    @PostMapping("/message")
    public ResponseEntity<ChatMessageDto> postUserMessage(@RequestBody ChatMessageDto chatMessageDto) {
        ChatMessage chatMessage = modelMapper.map(chatMessageDto, ChatMessage.class);
        ChatMessage savedMessage = chatService.saveUserMessage(chatMessage);
        ChatMessageDto savedMessageDto = modelMapper.map(savedMessage, ChatMessageDto.class);
        return ResponseEntity.ok(savedMessageDto);
    }

    // 대화 기록 조회
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<ChatMessageDto>> getChatHistory(@PathVariable Long userId) {
        List<ChatMessage> chatMessages = chatService.getChatHistory(userId);
        List<ChatMessageDto> chatMessageDtos = chatMessages.stream()
                .map(message -> modelMapper.map(message, ChatMessageDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(chatMessageDtos);
    }
}
