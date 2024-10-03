package com.overmind.overmind_chatbot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.overmind.overmind_chatbot.dto.QuestionDto;
import com.overmind.overmind_chatbot.exception.QuestionNotFoundException;
import com.overmind.overmind_chatbot.exception.UserNotFoundException;
import com.overmind.overmind_chatbot.entity.Question;
import com.overmind.overmind_chatbot.repository.QuestionRepository;
import com.overmind.overmind_chatbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RestController
@RequestMapping("/api/questions")
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    // 질문 등록
    @PostMapping("/questions")
    public Question createQuestion(QuestionDto questionDto) {
        Question question = new Question();

        question.setTitle(questionDto.getTitle());
        question.setContent(questionDto.getContent());
        question.setStatus(questionDto.getStatus());
        question.setCreatedAt(questionDto.getCreatedAt());
        question.setUpdatedAt(questionDto.getUpdatedAt());
        question.setUser(userRepository.findById(questionDto.getUser().getId())
        .orElseThrow(() -> new UserNotFoundException("User not found")));

        return questionRepository.save(question);
    }

    // 질문 목록 조회
    @GetMapping("/questions")
    public Page<Question> getQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    // 특정 질문 조회
    @GetMapping("/questions/{id}")
    public Question getQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException("Question not found"));
    }

    // 질문 수정
    @PutMapping("/questions/{id}")
    public Question updateQuestion(Long id, QuestionDto questionDto) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException("Question not found"));
        
        question.setTitle(questionDto.getTitle());
        question.setContent(questionDto.getContent());
        question.setStatus(questionDto.getStatus());
        question.setUpdatedAt(questionDto.getUpdatedAt());
        question.setUser(userRepository.findById(questionDto.getUser().getId())
        .orElseThrow(() -> new UserNotFoundException("User not found")));

        return questionRepository.save(question);
    }

    // 질문 삭제
    @DeleteMapping("/questions/{id}")
    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException("Question not found"));
        questionRepository.delete(question);
    }

    // 전체 질문 삭제
    @DeleteMapping("/questions")
    public void deleteAllQuestions() {
        questionRepository.deleteAll();
    }
}
