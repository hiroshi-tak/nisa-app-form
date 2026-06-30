package com.example.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.backend.dto.GeminiResponse;

import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String BASE_URL =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

    public String explainMonteCarlo(String prompt) {

        RestTemplate restTemplate = new RestTemplate();

        String url = BASE_URL + "?key=" + apiKey;

        Map<String, Object> body = Map.of(
            "contents", new Object[]{
                Map.of("parts", new Object[]{
                    Map.of("text", prompt)
                })
            }
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        int retry = 3;

        for (int i = 0; i < retry; i++) {
            try {

                ResponseEntity<GeminiResponse> response = restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        request,
                        GeminiResponse.class);

                GeminiResponse geminiResponse = response.getBody();

                var responseBody = geminiResponse;

                if (responseBody == null ||
                        responseBody.candidates() == null ||
                        responseBody.candidates().isEmpty()) {
                    return "AI応答が不正でした";
                }

                var candidate = responseBody.candidates().get(0);

                if (candidate.content() == null ||
                        candidate.content().parts() == null ||
                        candidate.content().parts().isEmpty()) {
                    return "AI応答が不正でした";
                }

                return candidate.content().parts().get(0).text();

            } catch (HttpClientErrorException.TooManyRequests e) {

                //e.printStackTrace();

                return "Gemini API利用上限に達しました。時間をおいて再実行してください。";

            }catch (Exception e) {

                //e.printStackTrace();

                if (i == retry - 1) {
                    return "現在AIが混雑しています。少し時間をおいて再実行してください。";
                }

                try {
                    Thread.sleep(1000L * (i + 1)); // 1s,2s,3s
                } catch (InterruptedException ignored) {}
            }
        }

    return "AI応答失敗";
    }
}