package com.springai.spring_ai_intro.services;

import com.springai.spring_ai_intro.model.*;

public interface OpenAIService {
    String getAnswer(String question);

    Answer getAnswer(Question question);

    Answer getCapital(GetCapitalRequest stateOrCountry);

    GetCapitalResponse getCapitalInJson(GetCapitalRequest getCapitalRequest);

    GetCaptialAndInfoResponse getCapitalAndInfoInJson(GetCapitalRequest getCapitalRequest);
}
