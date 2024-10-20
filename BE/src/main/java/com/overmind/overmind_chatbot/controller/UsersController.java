package com.overmind.overmind_chatbot.controller;


import com.overmind.overmind_chatbot.dto.QuestionRecordDTO;

import com.overmind.overmind_chatbot.entity.User;
import com.overmind.overmind_chatbot.repository.QuestionRepository;
import com.overmind.overmind_chatbot.repository.UsersRepository;
import com.overmind.overmind_chatbot.service.QuestionService;
import com.overmind.overmind_chatbot.service.UsersService;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UsersController {

    private final UsersService usersService;
    private final UsersRepository usersRepository;
    private final QuestionService questionService;

    public UsersController(UsersService usersService, UsersRepository usersRepository, QuestionRepository questionService, QuestionService questionService1) {
        this.usersService = usersService;
        this.usersRepository = usersRepository;
        this.questionService = questionService1;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        // 로그인 체크 후 세션 설정
        ResponseEntity<String> response = usersService.LoginCheck(loginData);
        if (response.getStatusCode().is2xxSuccessful()) {
            // 로그인 성공 시 세션에 사용자 정보 저장
            session.setAttribute("user_id", loginData.get("user_id"));
        }
        return response;
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // 세션 무효화 (로그아웃)
        session.invalidate();
        return ResponseEntity.ok("로그아웃 성공!");
    }

    @GetMapping("/checkSession")
    public ResponseEntity<String> checkSession(HttpSession session) {
        // 세션 확인
        System.out.println(session);
        String user_id = (String) session.getAttribute("user_id");
        System.out.println(user_id);
        if (user_id != null) {
            return ResponseEntity.ok("로그인된 사용자: " + user_id);
        } else {
            return ResponseEntity.status(401).body("로그인된 사용자가 없습니다.");
        }
    }

    //기록 확인
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
}