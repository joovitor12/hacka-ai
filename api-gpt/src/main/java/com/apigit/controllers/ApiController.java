package com.apigit.controllers;

import com.apigit.entities.GptModel;
import com.apigit.entities.GptResponseMessage;
import com.apigit.enums.ComponentEnum;
import com.apigit.services.ApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



@RestController
public class ApiController {

    @Autowired
    private ApiService service;

    @PostMapping(value = "/create-insight")
    @CrossOrigin(origins = "https://secure-backend-api.stilingue.com.br")
    public ResponseEntity<GptResponseMessage> createInsight(@RequestBody String insight, @RequestParam ComponentEnum component) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("hack-ai-team-name", "YOUR_TEAM_NAME");
        headers.add("hack-ai-api-key", "YOUR_API_KEY");

        GptModel response = new GptModel();
        service.setData(response, component, insight);

        HttpEntity<GptModel> requestEntity = new HttpEntity<>(response, headers);

        String url = "https://secure-backend-api.stilingue.com.br/blip-nlu-hack-ai/prod/hack-ai/chat/completions";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class );
        String jsonString = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);


        String content = rootNode.path("choices").get(0).path("message").path("content").asText();

        GptResponseMessage responseMessage = new GptResponseMessage(content);

        return ResponseEntity.ok().body(responseMessage);

    }


}
