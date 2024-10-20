package com.overmind.overmind_chatbot.controller;
import com.overmind.overmind_chatbot.dto.QuestionRecordDTO;
import com.overmind.overmind_chatbot.dto.QuestionUpdateRequestDTO;
import com.overmind.overmind_chatbot.entity.User;
import com.overmind.overmind_chatbot.repository.UsersRepository;
import com.overmind.overmind_chatbot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final UsersRepository usersRepository;

    @Autowired
    public QuestionController(QuestionService questionService, UsersRepository usersRepository) {
        this.questionService = questionService;
        this.usersRepository = usersRepository;
    }
    //기록 찾기
    @GetMapping("/user-records")
    public List<QuestionRecordDTO> getUserRecords(@RequestParam("user_id") String user_Id) {
        // UsersRepository를 사용하여 User 객체를 조회
        User user = usersRepository.findByUserId(user_Id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // User 객체에서 ID(Long 타입)를 추출
        Long userId = user.getId();

        // 추출한 userId로 QuestionRecordDTO 목록을 가져옴
        return questionService.getUserRecords(userId);
}

    @PutMapping("/update")
    public ResponseEntity<String> updateQuestion(@RequestBody QuestionUpdateRequestDTO request) {
        try {
            questionService.updateQuestion(request.getRecordId(), request.getQuestion(), request.getAnswer());
            return ResponseEntity.ok("Record updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating record.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteQuestion(@RequestParam Long recordId) {
        try {
            questionService.deleteQuestion(recordId);
            return ResponseEntity.ok("Record deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting record.");
        }
    }
}
