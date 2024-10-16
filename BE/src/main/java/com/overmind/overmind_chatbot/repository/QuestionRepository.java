package com.overmind.overmind_chatbot.repository;

import com.overmind.overmind_chatbot.entity.Question;
import com.overmind.overmind_chatbot.entity.enums.QuestionStatus;
import com.overmind.overmind_chatbot.entity.enums.Visibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByTitleContaining(String title, Pageable pageable);
    Page<Question> findByContentContaining(String content, Pageable pageable);
    Page<Question> findByStatus(QuestionStatus status, Pageable pageable);
    Page<Question> findByVisibility(Visibility visibility, Pageable pageable);
    Page<Question> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<Question> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
