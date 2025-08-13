package com.springai.spring_ai_functions.services;

import com.springai.spring_ai_functions.functions.WeatherServiceFunction;
import com.springai.spring_ai_functions.model.Answer;
import com.springai.spring_ai_functions.model.Question;
import com.springai.spring_ai_functions.model.WeatherRequest;
import com.springai.spring_ai_functions.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${aifunc.aiapp.ninjasApiKey}")
    private String ninjaApiKey;

    final OpenAiChatModel openAiChatModel;
//    final ChatClient client;

    @Override
    public Answer getAnswer(Question question) {
        var promptOptions = OpenAiChatOptions.builder()
                .toolCallbacks(List.of(FunctionToolCallback.builder("CurrentWeather", new WeatherServiceFunction(ninjaApiKey))
                        .description("Get the current weather for a location")
                        .toolCallResultConverter((response, responseType) -> {
                            String schema = ModelOptionsUtils.getJsonSchema(WeatherResponse.class, false);
                            String json = ModelOptionsUtils.toJsonString(response);
                            return schema + "\n" + json;
                        })
                        .inputType(WeatherRequest.class)
                        .build()))
                .build();
        Message userMessage = new PromptTemplate(question.question()).createMessage();
        Message systemMessage = new SystemMessage("You are weather service. You receive weather information from a service which gives you the info based on the metric system. When answering the weather in an imperial country, you should convert the temperature to Fahrenheit and wind speed in miles per hour.");

        var response = openAiChatModel.call(new Prompt(List.of(userMessage, systemMessage), promptOptions));
        return new Answer(response.getResult().getOutput().getText());
    }

    @Bean
    @Description("Get the current weather for the location")
    public Function<WeatherRequest, WeatherResponse> currentWeather() {
        return new WeatherServiceFunction(ninjaApiKey);
    }
}
