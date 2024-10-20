package com.overmind.overmind_chatbot.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Record_ID

    // 질문 내용
    @Column(name = "question", columnDefinition = "TEXT", nullable = false)
    private String question;  // 질문

    // 답변 내용
    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;  // 답변

    // 답변 생성 시간
    @Column(name = "answer_time")
    private LocalDateTime answerTime;  // 답변 시간

    // 답변 여부 ("O" 또는 "X")
    @Column(name = "has_answer", length = 1, nullable = false)
    private String hasAnswer = "X";  // 답변 여부

    // 사용자 정보
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 유저 ID

    // 메시지 생성 시간 (질문이 생성된 시간)
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
