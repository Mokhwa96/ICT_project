package com.overmind.overmind_chatbot.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {
    @GetMapping
    public String getQuestion(){return "";};

    @PostMapping
    public String postQuestion(){return "";};

    @PatchMapping
    public String updateQuestion(){return "";};
    //Question 테이블에는
}
