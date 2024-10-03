package com.overmind.overmind_chatbot.repository;

import com.overmind.overmind_chatbot.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByTitleContaining(String title, Pageable pageable);
    Page<Question> findByContentContaining(String content, Pageable pageable);
    Page<Question> findByUser_Id(Long userId, Pageable pageable);
    Page<Question> findByStatus(String status, Pageable pageable);
    Page<Question> findByVisibility(String visibility, Pageable pageable);
    Page<Question> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<Question> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}