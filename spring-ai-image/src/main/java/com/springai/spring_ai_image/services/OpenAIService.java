package com.springai.spring_ai_image.services;

import com.springai.spring_ai_image.model.Question;
import org.springframework.web.multipart.MultipartFile;

public interface OpenAIService {
    byte[] getImage(Question question);

    String getDescription(MultipartFile image);
}
