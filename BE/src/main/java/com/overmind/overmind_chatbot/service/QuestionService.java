package com.overmind.overmind_chatbot.service;

import com.overmind.overmind_chatbot.entity.Question;
import com.overmind.overmind_chatbot.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // 질문 등록
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    // 질문 목록 조회
    public Page<Question> getQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size));
    }

    // 특정 질문 조회
    public Question getQuestion(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    // 질문 수정
    public Question updateQuestion(Long id, Question updatedQuestion) {
        return questionRepository.findById(id).map(question -> {
            question.setTitle(updatedQuestion.getTitle());
            question.setContent(updatedQuestion.getContent());
            question.setStatus(updatedQuestion.getStatus());
            question.setVisibility(updatedQuestion.getVisibility());
            return questionRepository.save(question);
        }).orElse(null);
    }

    // 질문 삭제
    public boolean deleteQuestion(Long id) {
        return questionRepository.findById(id).map(question -> {
            questionRepository.delete(question);
            return true;
        }).orElse(false);
    }

    // 전체 질문 삭제
    public void deleteAllQuestions() {
        questionRepository.deleteAll();
    }
}
