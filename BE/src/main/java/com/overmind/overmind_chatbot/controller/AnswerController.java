package com.overmind.overmind_chatbot.controller;

import com.overmind.overmind_chatbot.entity.Answer;
import com.overmind.overmind_chatbot.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( value = "/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;
    //실시간 응답 처리
    @GetMapping("/{questionId}")
    public ResponseEntity AnswerGet(@PathVariable Long questionId){
        List<Answer> answers = answerService.getAnswersByQuestionId(questionId);
        if (answers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(answers);
    }

    // POST: /answers
    @PostMapping
    public ResponseEntity<Answer> createAnswer(@RequestBody Answer answer) {
        Answer savedAnswer = answerService.createAnswer(answer);
        return ResponseEntity.ok(savedAnswer);
    }

    // PATCH: /answers/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable Long id, @RequestBody Answer newContent) {
        Optional<Answer> updatedAnswer = answerService.updateAnswer(id, newContent.getContent());
        return updatedAnswer
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE: /answers/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
