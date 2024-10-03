package com.overmind.overmind_chatbot.dto;

import com.overmind.overmind_chatbot.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class QuestionDto {
    private Long id;
    private String title;
    private String content;
    private String status;
    private String visibility;
    private String createdAt;
    private String updatedAt;
    private UserDto user;
}