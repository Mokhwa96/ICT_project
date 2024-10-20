package com.overmind.overmind_chatbot.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.overmind.overmind_chatbot.entity.Answer;
import com.overmind.overmind_chatbot.entity.Question;
import com.overmind.overmind_chatbot.entity.User;
import com.overmind.overmind_chatbot.repository.AnswerRepository;
import com.overmind.overmind_chatbot.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public List<Answer> getAnswersByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    @Transactional
    public Answer saveAnswer(Long questionId, String content, User userId) {
        // 질문을 먼저 조회
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        // 새 Answer 객체 생성
        Answer answer = new Answer();
        answer.setQuestion(question);  // 외래 키로 Question 설정
        answer.setContent(content);
        answer.setUser(userId);  // 답변 작성자의 ID 설정
        answer.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        // 답변 저장
        return answerRepository.save(answer);
    }

    public Optional<Answer> updateAnswer(Long id, String content) {
        return answerRepository.findById(id).map(answer -> {
            answer.setContent(content);
            return answerRepository.save(answer);
        });
    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }


}
