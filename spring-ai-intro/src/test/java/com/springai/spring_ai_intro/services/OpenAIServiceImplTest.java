package com.springai.spring_ai_intro.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAIServiceImplTest {

    @Autowired
    OpenAIServiceImpl openAIService;

    @Test
    void getAnswer(){
        String ans = openAIService.getAnswer("just say - hi, very very nice to meet you!!");

        System.out.println(ans);
    }
}