package com.overmind.overmind_chatbot.controller;

import com.overmind.overmind_chatbot.entity.Answer;
import com.overmind.overmind_chatbot.entity.Question;
import com.overmind.overmind_chatbot.entity.User;
import com.overmind.overmind_chatbot.entity.enums.QuestionStatus;
import com.overmind.overmind_chatbot.service.AnswerService;
import com.overmind.overmind_chatbot.service.ChatbotService;
import com.overmind.overmind_chatbot.service.QuestionService;
import com.overmind.overmind_chatbot.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Map;

@RestController
public class ChatbotController {

    private final ChatbotService chatbotService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UsersService usersService;

    @Autowired
    public ChatbotController(ChatbotService chatbotService, QuestionService questionService, AnswerService answerService, UsersService usersService) {
        this.chatbotService = chatbotService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.usersService = usersService;
    }

    @PostMapping("/send-to-google-ai")
    public ResponseEntity<String> sendToGoogleAI(
            @RequestBody Map<String, String> payload,
            @SessionAttribute("user_id") String uid) {
        try {
            String userInput = payload.get("content");
            System.out.println("Received userInput: " + userInput);
            System.out.println("Received uid from session: " + uid);

            // 세션에서 받은 uid로 User 객체를 조회
            User user = usersService.findByUserId(uid)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            System.out.println("Found user: " + user);

            // 질문 저장 (등록 상태)
            Question savedQuestion = questionService.saveQuestion(userInput, user);
            System.out.println("Saved question with ID: " + savedQuestion.getId());

            // 질문이 저장된 후에 AI에게 요청을 보냄
            //String botResponse = chatbotService.sendToGoogleAI(userInput);
            String botResponse = "임시 테스트용 답변";
            System.out.println("Bot response: " + botResponse);

            // 질문 상태 변경 (답변 완료)
            long questionId = savedQuestion.getId();
            questionService.updateQuestionStatus(questionId, QuestionStatus.QUESTION_ANSWERED);
            answerService.saveAnswer(questionId, botResponse, user);
            System.out.println("Answer saved successfully");

            return ResponseEntity.ok(botResponse);
        } catch (Exception e) {
            e.printStackTrace();  // 예외의 스택 트레이스를 출력하여 문제의 원인을 확인
            return ResponseEntity.status(500).body("현재 연결이 안되고 있어요ㅠㅠ");
        }
    }
}