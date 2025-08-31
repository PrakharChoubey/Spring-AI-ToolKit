package com.springai.spring_ai_audio.services;


import lombok.RequiredArgsConstructor;
import com.springai.spring_ai_audio.model.Question;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final OpenAiAudioSpeechModel openAiAudioSpeechModel;

    @Override
    public byte[] getSpeech(Question question) {
        var options = OpenAiAudioSpeechOptions.builder()
                .model(OpenAiAudioApi.TtsModel.TTS_1.value)
                .voice(OpenAiAudioApi.SpeechRequest.Voice.ASH)
                .speed(1.0f)
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .build();

        SpeechPrompt speechPrompt = new SpeechPrompt(question.question(), options);
        SpeechResponse response = openAiAudioSpeechModel.call(speechPrompt);

        return response.getResult().getOutput();

    }
}
