package com.overmind.overmind_chatbot.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageDto {
    private Long id;  // Record_ID
    private String question;  // 질문
    private String answer;  // 답변
    private String answerTime;  // 답변 시간 (문자열로 변환)
    private Long userId;  // 유저 ID
    private String hasAnswer;  // 답변 여부 ("O" 또는 "X")
}
