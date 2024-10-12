package com.overmind.overmind_chatbot.controller;


import com.overmind.overmind_chatbot.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    privateUsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        System.out.println(session);
        System.out.println("controller - loginData :" + loginData);
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
}