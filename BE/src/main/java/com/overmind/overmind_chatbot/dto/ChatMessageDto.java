package com.overmind.overmind_chatbot.dto;

import com.overmind.overmind_chatbot.entity.enums.MessageType;
import com.overmind.overmind_chatbot.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageDto {
    private Long id;
    private MessageType messageType;
    private String content;
    private LocalDateTime createdAt;
    private UserDto user;
}
