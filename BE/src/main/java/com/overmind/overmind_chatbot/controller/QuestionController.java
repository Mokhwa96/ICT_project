package com.overmind.overmind_chatbot.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.overmind.overmind_chatbot.dto.QuestionDto;
import com.overmind.overmind_chatbot.entity.Question;
import com.overmind.overmind_chatbot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ModelMapper modelMapper;

    public QuestionController(QuestionService questionService, ModelMapper modelMapper){
        this.questionService = questionService;
        this.modelMapper = modelMapper;
    }

    // 질문 등록
    @PostMapping
    public ResponseEntity<QuestionDto> postQuestion(@RequestBody QuestionDto questionDto){
        Question question = modelMapper.map(questionDto, Question.class);
        Question savedQuestion = questionService.createQuestion(question);
        QuestionDto savedQuestionDto = modelMapper.map(savedQuestion, QuestionDto.class);
        return ResponseEntity.ok(savedQuestionDto);
    }

    // 질문 목록 조회
    @GetMapping
    public ResponseEntity<Page<QuestionDto>> getQuestions(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size){
        Page<Question> questions = questionService.getQuestions(page, size);
        Page<QuestionDto> questionDtos = questions.map(question -> modelMapper.map(question, QuestionDto.class));
        return ResponseEntity.ok(questionDtos);
    }

    // 특정 질문 조회
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable Long id){
        Question question = questionService.getQuestion(id);
        QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);
        return ResponseEntity.ok(questionDto);
    }

    // 질문 수정
    @PutMapping("/{id}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable Long id, @RequestBody QuestionDto questionDto){
        Question question = modelMapper.map(questionDto, Question.class);
        Question updatedQuestion = questionService.updateQuestion(id, question);
        QuestionDto updatedQuestionDto = modelMapper.map(updatedQuestion, QuestionDto.class);
        return ResponseEntity.ok(updatedQuestionDto);
    }

    // 질문 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
    
    // 전체 질문 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteAllQuestions(){
        questionService.deleteAllQuestions();
        return ResponseEntity.noContent().build();
    }
}
