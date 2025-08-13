package com.springai.spring_ai_functions.services;

import com.springai.spring_ai_functions.model.Answer;
import com.springai.spring_ai_functions.model.Question;

public interface OpenAIService {
    Answer getAnswer(Question question);
}
