package com.overmind.overmind_chatbot.controller;

import com.overmind.overmind_chatbot.entity.Answer;
import com.overmind.overmind_chatbot.entity.Question;
import com.overmind.overmind_chatbot.entity.User;
import com.overmind.overmind_chatbot.entity.enums.QuestionStatus;
import com.overmind.overmind_chatbot.service.AnswerService;
import com.overmind.overmind_chatbot.service.ChatbotService;
import com.overmind.overmind_chatbot.service.QuestionService;
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

    @Autowired
    public ChatbotController(ChatbotService chatbotService, QuestionService questionService, AnswerService answerService) {
        this.chatbotService = chatbotService;
        this.questionService = questionService;
        this.answerService = answerService;
    }


    @PostMapping("/send-to-google-ai")
    public ResponseEntity<String> sendToGoogleAI(
            @RequestBody Map<String, String> payload,
            @SessionAttribute("user_id") String uid) {
        try {
            String userInput = payload.get("content");
            String title = payload.get("title");

            // 질문 저장 (등록 상태)
            Question savedQuestion = questionService.saveQuestion(title, userInput, uid); // userId 사용
            // 질문이 저장된 후에 AI에게 요청을 보냄
            String botResponse = chatbotService.sendToGoogleAI(userInput);
            //String botResponse = "임시 테스트용 답변";
            // 질문 상태 변경 (답변 완료)
            Long questionId = savedQuestion.getId();
            questionService.updateQuestionStatus(questionId, QuestionStatus.QUESTION_ANSWERED);
            answerService.saveAnswer(questionId, botResponse, uid);
            return ResponseEntity.ok(botResponse);
        } catch (Exception e) {
            //return ResponseEntity.status(500).body("Error processing request: " + e.getMessage());
            return ResponseEntity.status(500).body("현재 연결이 안되고 있어요ㅠㅠ");
        }
    }
}