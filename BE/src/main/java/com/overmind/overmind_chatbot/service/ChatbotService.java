package com.overmind.overmind_chatbot.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatbotService {

    @Value("${google.api.key}")
    private String apiKey;

    public String sendToGoogleAI(String userInput) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String googleApiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> textPart = new HashMap<>();
        textPart.put("text", userInput);

        Map<String, Object> parts = new HashMap<>();
        parts.put("parts", List.of(textPart));

        Map<String, Object> contents = new HashMap<>();
        contents.put("contents", List.of(parts));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(contents, headers);

        // Google AI로 POST 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(googleApiUrl, request, String.class);

        // 응답 데이터에서 'text' 필드 추출
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray candidates = jsonResponse.getJSONArray("candidates");
        String botResponse = candidates.getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text");

        return botResponse;
    }
}
