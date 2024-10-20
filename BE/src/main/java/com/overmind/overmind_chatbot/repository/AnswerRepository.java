package com.overmind.overmind_chatbot.repository;

import com.overmind.overmind_chatbot.entity.Answer;
import com.overmind.overmind_chatbot.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
    List<Answer> findByQuestion(Question question);
    Optional<Answer> findByQuestion_Id(Long recordId);
}