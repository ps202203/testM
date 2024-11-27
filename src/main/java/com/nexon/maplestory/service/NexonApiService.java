package com.nexon.maplestory.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONObject;

@Service
public class NexonApiService {

    @Value("${nexon.api.key}")
    private String nexonApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // 캐릭터 이름을 이용한 API 호출 메서드
    public JSONObject getCharacterInfo(String apiKey, String characterName) {
        String url = "https://open.api.nexon.com/maplestorym/v1/character?apikey=" + apiKey + "&characterName=" + characterName;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + nexonApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if(response.getStatusCode() == HttpStatus.OK){
            return new JSONObject(response.getBody());
        } else {
            // 예외 처리
            throw new RuntimeException("API 호출 실패: " + response.getStatusCode());
        }
    }
}