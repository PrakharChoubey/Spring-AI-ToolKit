package com.springai.spring_ai_rag.services;

import com.springai.spring_ai_rag.model.Answer;
import com.springai.spring_ai_rag.model.Question;

public interface OpenAIService {

    public Answer getAnswer(Question question);
}
