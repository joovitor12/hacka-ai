package com.apigit.controller;

import com.apigit.entities.ApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {

    @PostMapping(value = "/post-question")
    @CrossOrigin(origins = "https://secure-backend-api.stilingue.com.br")
    public ResponseEntity<String> postQuestion(@RequestBody ApiResponse body){
        setData(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("hack-ai-team-name", "studai");
        headers.add("hack-ai-api-key", "6bv6E7biA840N6a5r232og");
        HttpEntity<ApiResponse> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                "https://secure-backend-api.stilingue.com.br/blip-nlu-hack-ai/prod/hack-ai/chat/completions",
                HttpMethod.POST,
                requestEntity,
                String.class);

        return response;
    }


    public void setData(ApiResponse body){
        Map<String, Double> logitBias = new HashMap<>();
        body.setDeployment("gpt-35-turbo");
        body.setTemperature(1);
        body.setN(1);
        body.setStop("string");
        body.setMaxTokens(4096);
        body.setPresencePenalty(0);
        body.setFrequencyPenalty(0);
        logitBias.put("", 0.0);
        body.setLogitBias(logitBias);
        body.setUser("studai");
    }
}
