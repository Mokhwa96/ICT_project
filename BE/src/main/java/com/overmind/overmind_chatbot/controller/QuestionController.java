package com.overmind.overmind_chatbot.controller;
import com.overmind.overmind_chatbot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;


    // 질문 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    // 전체 질문 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteAllQuestions() {
        questionService.deleteAllQuestions();
        return ResponseEntity.noContent().build();
    }
}
