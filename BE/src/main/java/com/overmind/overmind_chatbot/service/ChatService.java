package com.overmind.overmind_chatbot.service;

import com.overmind.overmind_chatbot.entity.ChatMessage;
import com.overmind.overmind_chatbot.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // 질문 저장 (답변 없이)
    public ChatMessage saveQuestion(ChatMessage chatMessage) {
        chatMessage.setHasAnswer("X");
        chatMessage.setAnswer(null);
        chatMessage.setAnswerTime(null);
        return chatMessageRepository.save(chatMessage);
    }

    // 답변 저장 및 업데이트
    public ChatMessage saveAnswer(Long id, String answer) {
        ChatMessage existingMessage = chatMessageRepository.findById(id).orElse(null);
        if (existingMessage != null) {
            existingMessage.setAnswer(answer);
            existingMessage.setAnswerTime(LocalDateTime.now());
            existingMessage.setHasAnswer("O");
            return chatMessageRepository.save(existingMessage);
        }
        return null;
    }

    // 대화 기록 조회
    public List<ChatMessage> getChatHistory(Long userId) {
        return chatMessageRepository.findByUserId(userId);
    }

    // 메시지 수정
    public ChatMessage updateMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    // 메시지 삭제
    public void deleteMessage(Long id) {
        chatMessageRepository.deleteById(id);
    }
}
