package com.overmind.overmind_chatbot.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class QuestionRecordDTO {
    private Long recordId;
    private String question;
    private String answer;
    private Timestamp answerTime;
    private String userId;
    private boolean isAnswered;

    public QuestionRecordDTO(Long recordId, String question, String answer, Timestamp answerTime, String userId, boolean isAnswered) {
        this.recordId = recordId;
        this.question = question;
        this.answer = answer;
        this.answerTime = answerTime;
        this.userId = userId;
        this.isAnswered = isAnswered;
    }
}
