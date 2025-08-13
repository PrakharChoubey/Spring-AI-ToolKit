package com.springai.spring_ai_functions.controllers;

import com.springai.spring_ai_functions.model.Answer;
import com.springai.spring_ai_functions.model.Question;
import com.springai.spring_ai_functions.services.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    @PostMapping("/weather")
    public Answer askQuestion(@RequestBody Question question){

        return openAIService.getAnswer(question);
    }
}
