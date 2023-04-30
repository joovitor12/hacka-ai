package com.apigit.entities;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ApiResponse {
    private String deployment;
    private List<Message> messages;
    private int temperature;
    private int n;
    private String stop;
    private int maxTokens;
    private int presencePenalty;
    private int frequencyPenalty;
    private Map<String, Double> logitBias;
    private String user;
}
