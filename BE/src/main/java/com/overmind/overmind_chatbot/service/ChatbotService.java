package com.overmind.overmind_chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

    public Map<String, Object> analyzeChat(List<String> chatHistory) {
        // API 호출을 위한 HTTP 클라이언트 설정
        RestTemplate restTemplate = new RestTemplate();
        String googleApiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + apiKey;

        // 프롬프트 생성: 후카츠 기법을 사용하여 AI에게 응답 형식을 지시
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("You are a medical assistant. Based on the following chat history, please provide a JSON response with the fields: ")
                .append("\"symptoms\", \"disease\", and \"hospital_type\". Ensure the response is in JSON format. If you cannot, return an error. ")
                .append("Here is the chat history:\n");

        for (String message : chatHistory) {
            promptBuilder.append("- ").append(message).append("\n");
        }

        // JSON 형식에 맞게 데이터를 구성
        Map<String, Object> payload = new HashMap<>();
        List<Map<String, Object>> contents = new ArrayList<>();

        Map<String, Object> part = new HashMap<>();
        part.put("text", promptBuilder.toString());

        Map<String, Object> content = new HashMap<>();
        content.put("parts", Collections.singletonList(part));

        contents.add(content);
        payload.put("contents", contents);

        try {
            // Google AI Studio에 요청 전송
            ResponseEntity<Map> response = restTemplate.postForEntity(googleApiUrl, payload, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> aiResponse = response.getBody();

                // AI가 반환한 JSON 형식 데이터가 있는지 확인
                if (aiResponse != null && aiResponse.containsKey("generatedContent")) {
                    // AI의 응답 파싱
                    String jsonResponse = (String) aiResponse.get("generatedContent");
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> result = objectMapper.readValue(jsonResponse, Map.class);

                    // 응답 형식에 따라 필요한 정보가 포함되어 있는지 확인
                    if (result.containsKey("symptoms") && result.containsKey("disease") && result.containsKey("hospital_type")) {
                        return result;
                    } else {
                        // JSON 형식이 올바르지 않으면 예시 데이터 반환
                        return getExampleData();
                    }
                } else {
                    // 응답이 없거나 JSON 형식이 아니면 예시 데이터 반환
                    return getExampleData();
                }
            } else {
                System.out.println("Error from AI Studio API: " + response.getStatusCode());
                return getExampleData();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getExampleData(); // 오류 발생 시 예시 데이터 반환
        }
    }

    // 예시 데이터를 반환하는 메서드
    private Map<String, Object> getExampleData() {
        Map<String, Object> exampleData = new HashMap<>();
        exampleData.put("symptoms", Arrays.asList("두통", "기침", "열"));
        exampleData.put("disease", "감기");
        exampleData.put("hospital_type", "내과");
        return exampleData;
    }
}
