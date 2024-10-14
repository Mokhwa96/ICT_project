package com.overmind.overmind_chatbot.service;

import com.overmind.overmind_chatbot.entity.ChatMessage;
import com.overmind.overmind_chatbot.entity.MessageType;
import com.overmind.overmind_chatbot.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // 사용자 메시지 저장
    public ChatMessage saveUserMessage(ChatMessage chatMessage) {
        chatMessage.setMessageType(MessageType.USER);
        return chatMessageRepository.save(chatMessage);
    }

    // 챗봇 메시지 저장
    public ChatMessage saveBotMessage(ChatMessage chatMessage) {
        chatMessage.setMessageType(MessageType.BOT);
        return chatMessageRepository.save(chatMessage);
    }

    // 대화 기록 조회
    public List<ChatMessage> getChatHistory(Long userId) {
        return chatMessageRepository.findByUserIdOrderByCreatedAtAsc(userId);
    }
}
