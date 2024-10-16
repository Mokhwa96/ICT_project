package com.overmind.overmind_chatbot.service;

import com.overmind.overmind_chatbot.entity.Question;
import com.overmind.overmind_chatbot.entity.User;
import com.overmind.overmind_chatbot.entity.enums.Visibility;
import com.overmind.overmind_chatbot.entity.enums.QuestionStatus;
import com.overmind.overmind_chatbot.repository.QuestionRepository;
import com.overmind.overmind_chatbot.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UsersRepository usersRepository;
    @Autowired
    public QuestionService(QuestionRepository questionRepository, UsersRepository usersRepository) {
        this.questionRepository = questionRepository;
        this.usersRepository = usersRepository;
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

    // 질문 저장하기
    @Transactional
    public Question saveQuestion(String title, String content, String userId) {
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setStatus(QuestionStatus.QUESTION_REGISTERED);
        question.setVisibility(Visibility.PUBLIC);
        question.setCreatedAt(LocalDateTime.now());
        question.setUid(userId); // 조회된 user를 설정

        return questionRepository.save(question);
    }
    // 질문 상태 바꾸기
    @Transactional
    public void updateQuestionStatus(Long questionId, QuestionStatus status) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        question.setStatus(status);
        questionRepository.save(question); // 변경된 상태 저장
    }


}
