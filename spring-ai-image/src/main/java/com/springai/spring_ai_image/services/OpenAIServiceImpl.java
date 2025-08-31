package com.springai.spring_ai_image.services;

import com.springai.spring_ai_image.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {

    @Autowired
    private ChatModel chatModel;
    private final ImageModel imageModel;
    private final OpenAiImageModel openAiImageModel;

    @Override
    public byte[] getImage(Question question) {
        var options = OpenAiImageOptions.builder()
                .height(256).width(256)
                .responseFormat("b64_json")
                .model("dall-e-2")
                .quality("hd")
                .style("natural")
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(question.question(), options);
        var imgResponse = openAiImageModel.call(imagePrompt);

        return Base64.getDecoder().decode(imgResponse.getResult().getOutput().getB64Json());
    }

    @Override
    public String getDescription(MultipartFile image) {
        var options = OpenAiChatOptions.builder()
                .model(OpenAiApi.ChatModel.GPT_4_O.getValue()).build();

        Message userMessage = UserMessage.builder().media(new Media(MimeTypeUtils.IMAGE_JPEG, image.getResource()))
                .text("Explain what do u see in the picture.").build();

        ChatResponse response = chatModel.call(Prompt.builder().messages(userMessage).chatOptions(options).build());

        return response.getResult().getOutput().getText();
    }
//
//    @Override
//    public byte[] getImage(Question question) {
//        var options = OpenAiImageOptions.builder()
//                .height(256).width(256)
//                .responseFormat("b64_json")
//                .model("dall-e-2")
//                .build();
//
//        ImagePrompt imagePrompt = new ImagePrompt(question.question(), options);
//        var imgResponse = imageModel.call(imagePrompt);
//
//        return Base64.getDecoder().decode(imgResponse.getResult().getOutput().getB64Json());
//    }
}
