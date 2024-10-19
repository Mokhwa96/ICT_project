package com.overmind.overmind_chatbot.repository;
import com.overmind.overmind_chatbot.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
