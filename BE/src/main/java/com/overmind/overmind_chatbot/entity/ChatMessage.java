package com.overmind.overmind_chatbot.entity;

import lombok.*;
import com.overmind.overmind_chatbot.entity.enums.MessageType;
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
    private Long id;

    // 메시지 타입 (USER 또는 BOT)
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false)
    private MessageType messageType;

    // 메시지 내용
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    // 메시지 생성 시간
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 사용자 정보 (Optional)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
