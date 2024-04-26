package com.stonewu.aifusion.module.ai.service.ai;

import com.esotericsoftware.minlog.Log;
import com.stonewu.aifusion.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.ai.dto.ModelDTO;
import com.stonewu.aifusion.module.ai.api.google.dto.*;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service("googleAiService")
@Slf4j
public class GoogleAiServiceImpl implements AiService {

    private String baseUrl = "https://generativelanguage.googleapis.com/";

    WebClient client = WebClient.builder().baseUrl(baseUrl)
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Accept", "text/event-stream")
            .defaultHeader("Cache-Control", "no-cache")
            .defaultHeader("Connection", "keep-alive")
            .build();

    @Resource
    private RestTemplate restTemplate;


    @Override
    public Flux<MessageResponse> streamChat(List<Message> messages, ModelDTO model) {

        // google gemini
        GeminiGCRequestDTO geminiGCRequestDTO = new GeminiGCRequestDTO();
        // 将messages转换成contents
        List<Content> contents = messages.stream().map(
                message -> Content.builder().role(message.getRole())
                        .parts(Collections.singletonList(ContentPart.builder().text(message.getContent()).build()))
                        .build()
        ).toList();
        geminiGCRequestDTO.setContents(contents);
        Flux<GeminiResponseDTO> chat = client.post().uri("/v1beta/models/" + model.getModelName() + ":streamGenerateContent?alt=sse&key=" + model.getApiKey())
                .bodyValue(geminiGCRequestDTO).retrieve().bodyToFlux(GeminiResponseDTO.class);
        Flux<MessageResponse> responseFlux = chat.map(geminiResponseDTO -> {
            try {
                String text = geminiResponseDTO.getCandidates().getFirst().getContent().getParts().getFirst().getText();
                Message message = Message.builder().role("model")
                        .content(text)
                        .build();
                return MessageResponse.builder().message(message).code(GlobalErrorCodeConstants.SUCCESS.getCode()).build();
            } catch (Exception e) {
                return MessageResponse.builder().message(null).code(GlobalErrorCodeConstants.SUCCESS.getCode()).build();
            }
        });
        return responseFlux;
    }

    @Override
    public Integer countToken(List<Message> messages, ModelDTO model) {
        List<Content> contents = messages.stream().map(
                message -> Content.builder().role(message.getRole())
                        .parts(Collections.singletonList(ContentPart.builder().text(message.getContent()).build()))
                        .build()
        ).toList();
        GeminiTokenRequestDTO geminiTokenRequestDTO = GeminiTokenRequestDTO.builder().contents(contents).build();
        GeminiTokenResponseDTO tokenResponse = restTemplate.postForObject(
                baseUrl + "/v1beta/models/" + model.getModelName() + ":countTokens?key=" + model.getApiKey(),
                geminiTokenRequestDTO, GeminiTokenResponseDTO.class);
//        GeminiTokenResponseDTO tokenResponse = client.post().uri("/v1beta/models/" + model.getModelName() + ":countTokens?key=" + model.getApiKey())
//                .bodyValue(geminiTokenRequestDTO).retrieve().bodyToMono(GeminiTokenResponseDTO.class).block();
        if (tokenResponse != null) {
            return tokenResponse.getTotalTokens();
        }
        return null;
    }
}
