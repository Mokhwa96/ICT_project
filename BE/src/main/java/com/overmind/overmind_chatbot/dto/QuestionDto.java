package com.overmind.overmind_chatbot.dto;

import com.overmind.overmind_chatbot.entity.enums.QuestionStatus;
import com.overmind.overmind_chatbot.entity.enums.Visibility;
import com.overmind.overmind_chatbot.dto.UserDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    private QuestionStatus status;
    private Visibility visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto user;
}
