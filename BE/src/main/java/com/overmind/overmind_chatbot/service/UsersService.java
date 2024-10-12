package com.overmind.overmind_chatbot.service;

import com.overmind.overmind_chatbot.entity.User;
import com.overmind.overmind_chatbot.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public ResponseEntity LoginCheck( Map<String, String> loginData) {
        String username = loginData.get("user_id");
        String password = loginData.get("password");
        User user = usersRepository.findByNameAndPassword(username,password);
        System.out.println(user);

        if (user != null && user.getPassword().equals(password)) {
            return ResponseEntity.ok("로그인 성공! 환영합니다.");
        } else {
            return ResponseEntity.status(401).body("로그인 실패! 사용자 이름 또는 비밀번호가 틀렸습니다.");
        }
    }
}
