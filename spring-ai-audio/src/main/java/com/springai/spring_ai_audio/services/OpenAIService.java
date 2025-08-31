package com.springai.spring_ai_audio.services;

import com.springai.spring_ai_audio.model.Question;

public interface OpenAIService {
    byte[] getSpeech(Question question);
}
