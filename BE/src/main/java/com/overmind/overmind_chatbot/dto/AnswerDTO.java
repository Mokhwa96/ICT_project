package com.overmind.overmind_chatbot.dto;

import com.overmind.overmind_chatbot.entity.Answer;
import com.overmind.overmind_chatbot.dto.QuestionDto;
import com.overmind.overmind_chatbot.dto.UserDto;
import java.time.LocalDateTime;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDTO {
    private Long id;
    private QuestionDto question;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto user;
}
