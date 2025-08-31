package com.springai.spring_ai_audio.controller;

import lombok.RequiredArgsConstructor;
import com.springai.spring_ai_audio.model.Question;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.springai.spring_ai_audio.services.OpenAIService;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    @PostMapping(value = "/talk", produces = {"audio/mpeg"})
    public byte[] getTalk(@RequestBody Question question) {
        return openAIService.getSpeech(question);
    }
}
