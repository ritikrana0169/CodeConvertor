package com.convertor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.convertor.model.ChatGPTRequest;
import com.convertor.model.ChatGptResponse;
import com.convertor.model.ChatGptResponse.Choice;

@RestController
public class ControllerClass {

    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt){
        ChatGPTRequest request=new ChatGPTRequest(model, prompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
//        return chatGptResponse.getChoices().get(0).getMessage().getContent();
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }
}