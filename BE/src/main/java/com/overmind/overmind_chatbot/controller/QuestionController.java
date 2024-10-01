package com.overmind.overmind_chatbot.controller;

import com.overmind.overmind_chatbot.entity.Answer;
import com.overmind.overmind_chatbot.entity.Question;
import com.overmind.overmind_chatbot.repository.QuestionRepository;
import com.overmind.overmind_chatbot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @PostMapping
    public ResponseEntity<Question> postQuestion(@RequestBody Question question){
        Question savedQuestion = questionService.createQuestion(question);
        return ResponseEntity.ok(savedQuestion);
    };
}
