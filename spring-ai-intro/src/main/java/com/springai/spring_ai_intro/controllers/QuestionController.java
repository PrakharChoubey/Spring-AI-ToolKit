package com.springai.spring_ai_intro.controllers;

import com.springai.spring_ai_intro.model.*;
import com.springai.spring_ai_intro.services.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    @Autowired
    OpenAIService openAIService;

    @PostMapping("/capital/info/json")
    public GetCaptialAndInfoResponse getCapitalAndInfoInJson(@RequestBody GetCapitalRequest getCapitalRequest){
        return openAIService.getCapitalAndInfoInJson(getCapitalRequest);
    }

    @PostMapping("/capital/json")
    public GetCapitalResponse getCapitalInJson(@RequestBody GetCapitalRequest getCapitalRequest){
        return openAIService.getCapitalInJson(getCapitalRequest);
    }

    @PostMapping("/capital")
    public Answer getCapitalWithInfo(@RequestBody GetCapitalRequest getCapitalRequest){
        return openAIService.getCapital(getCapitalRequest);
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

}
