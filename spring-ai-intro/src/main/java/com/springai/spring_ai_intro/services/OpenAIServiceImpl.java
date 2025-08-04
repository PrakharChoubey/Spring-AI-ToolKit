package com.springai.spring_ai_intro.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springai.spring_ai_intro.model.*;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatModel chatModel;

    @Autowired
    ObjectMapper objectMapper;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalResource;

    @Value("classpath:templates/get-capital-info-in-json-prompt.st")
    private Resource getCapitalInfoJsonResource;

    public OpenAIServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public Answer getCapital(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalResource);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));

        ChatResponse chatResponse = chatModel.call(prompt);

        return new Answer(chatResponse.getResult().getOutput().getText());
    }

    @Override
    public GetCapitalResponse getCapitalInJson(GetCapitalRequest getCapitalRequest) {
        BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);
        // use this (autogen prompt by spring) instead of, manual prompt to say --- "return me a json"
        // was using "Respond in JSON format without markdown tags, with name of the city in property named 'capital'"
        // in prompt-resource
        String format = converter.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalInfoJsonResource);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format));
        ChatResponse chatResponse = chatModel.call(prompt);

//        String jsonResponse;
//        try {
//            JsonNode jsonNode = objectMapper.readTree(chatResponse.getResult().getOutput().getText());
//            jsonResponse = jsonNode.get("answer").asText();
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }

        return converter.convert(Objects.requireNonNull(chatResponse.getResult().getOutput().getText()));
    }

    @Override
    public GetCaptialAndInfoResponse getCapitalAndInfoInJson(GetCapitalRequest getCapitalRequest) {
        BeanOutputConverter<GetCaptialAndInfoResponse> converter = new BeanOutputConverter<>(GetCaptialAndInfoResponse.class);
        String format = converter.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalInfoJsonResource);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format));
        ChatResponse chatResponse = chatModel.call(prompt);
        System.out.println("========GOT IT==========");
        System.out.println(chatResponse);
        return converter.convert(Objects.requireNonNull(chatResponse.getResult().getOutput().getText()));
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();

        ChatResponse chatResponse = chatModel.call(prompt);

        return chatResponse.getResult().getOutput().getText();
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();

        ChatResponse chatResponse = chatModel.call(prompt);

        return new Answer(chatResponse.getResult().getOutput().getText());
    }

}
