package com.overmind.overmind_chatbot.controller;

import com.overmind.overmind_chatbot.dto.ChatMessageDto;
import com.overmind.overmind_chatbot.entity.ChatMessage;
import com.overmind.overmind_chatbot.entity.User;
import com.overmind.overmind_chatbot.service.ChatService;
import com.overmind.overmind_chatbot.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private UsersService usersService;

    // 질문 저장
    @PostMapping("/question")
    public ChatMessageDto postQuestion(@RequestBody ChatMessageDto chatMessageDto) {
        User user = usersService.getUserById(chatMessageDto.getUserId());
        ChatMessage chatMessage = ChatMessage.builder()
                .question(chatMessageDto.getQuestion())
                .user(user)
                .build();
        ChatMessage savedMessage = chatService.saveQuestion(chatMessage);
        return toDto(savedMessage);
    }

    // 답변 저장
    @PostMapping("/answer/{id}")
    public ChatMessageDto postAnswer(@PathVariable Long id, @RequestBody String answer) {
        ChatMessage updatedMessage = chatService.saveAnswer(id, answer);
        if (updatedMessage != null) {
            return toDto(updatedMessage);
        } else {
            return null;  // 또는 예외 처리
        }
    }

    // 대화 기록 조회
    @GetMapping("/history/{userId}")
    public List<ChatMessageDto> getChatHistory(@PathVariable Long userId) {
        List<ChatMessage> chatMessages = chatService.getChatHistory(userId);
        return chatMessages.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 메시지 수정
    @PutMapping("/message/{id}")
    public ChatMessageDto updateMessage(@PathVariable Long id, @RequestBody ChatMessageDto chatMessageDto) {
        ChatMessage existingMessage = chatService.getMessageById(id);
        if (existingMessage != null) {
            existingMessage.setQuestion(chatMessageDto.getQuestion());
            existingMessage.setAnswer(chatMessageDto.getAnswer());
            existingMessage.setHasAnswer(chatMessageDto.getHasAnswer());
            existingMessage.setAnswerTime(chatMessageDto.getAnswerTime() != null ? LocalDateTime.parse(chatMessageDto.getAnswerTime()) : null);
            ChatMessage updatedMessage = chatService.updateMessage(existingMessage);
            return toDto(updatedMessage);
        } else {
            return null;  // 또는 예외 처리
        }
    }

    // 메시지 삭제
    @DeleteMapping("/message/{id}")
    public void deleteMessage(@PathVariable Long id) {
        chatService.deleteMessage(id);
    }

    // 엔티티를 DTO로 변환하는 메서드
    private ChatMessageDto toDto(ChatMessage chatMessage) {
        return ChatMessageDto.builder()
                .id(chatMessage.getId())
                .question(chatMessage.getQuestion())
                .answer(chatMessage.getAnswer())
                .answerTime(chatMessage.getAnswerTime() != null ? chatMessage.getAnswerTime().toString() : null)
                .userId(chatMessage.getUser().getId())
                .hasAnswer(chatMessage.getHasAnswer())
                .build();
    }
}
