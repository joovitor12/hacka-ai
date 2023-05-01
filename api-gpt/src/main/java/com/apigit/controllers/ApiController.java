package com.apigit.controllers;

import com.apigit.entities.ApiResponse;
import com.apigit.entities.ComponentContext;
import com.apigit.entities.Message;
import com.apigit.entities.MessageList;
import com.apigit.enums.ComponentEnum;
import com.apigit.services.ApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


@RestController
public class ApiController {
    @Autowired
    private ApiService service;

    @PostMapping(value = "/post-question")
    @CrossOrigin(origins = "https://secure-backend-api.stilingue.com.br")
    public ResponseEntity<String> postQuestion(@RequestBody ApiResponse body){
        service.setData(body, ComponentEnum.CONTATOS, body.getDeployment());
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

    @PostMapping(value = "/create-insight")
    @CrossOrigin(origins = "https://secure-backend-api.stilingue.com.br")
    public ResponseEntity<String> createInsight(@RequestBody String insight) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("hack-ai-team-name", "studai");
        headers.add("hack-ai-api-key", "6bv6E7biA840N6a5r232og");

        ApiResponse response = new ApiResponse();
        service.setData(response, ComponentEnum.CONTATOS, insight);

        HttpEntity<ApiResponse> requestEntity = new HttpEntity<>(response, headers);

        String url = "https://secure-backend-api.stilingue.com.br/blip-nlu-hack-ai/prod/hack-ai/chat/completions";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class );
        String jsonString = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);


        String content = rootNode.path("choices").get(0).path("message").path("content").asText();


        return ResponseEntity.ok().body(content);

    }


}
