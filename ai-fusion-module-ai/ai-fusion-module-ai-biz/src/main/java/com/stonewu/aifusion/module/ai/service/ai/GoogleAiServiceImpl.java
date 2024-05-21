package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.ai.dto.ModelDTO;
import com.stonewu.aifusion.module.ai.api.google.dto.*;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

        String baseUrl = model.getBaseApi();
        if (StringUtils.isEmpty(baseUrl)) {
            baseUrl = this.baseUrl;
        }
        /*VertexAI vertexApi =  new VertexAI(projectId, location);

        var chatClient = new VertexAiGeminiChatClient(vertexApi,
                VertexAiGeminiChatOptions.builder()
                        .withModel(model.getModelName())
                        .withTemperature(0.4F)
                        .build());
        List<org.springframework.ai.chat.messages.Message> messageList = messages.stream().map(message -> {
            if (message.getRole().equals("user")) {
                return new UserMessage(message.getContent());
            } else if (message.getRole().equals("assistant")) {
                return new AssistantMessage(message.getContent());
            }
            return new AssistantMessage(message.getContent());
        }).collect(Collectors.toList());

        Flux<ChatResponse> hello = chatClient.stream(new Prompt(messageList));*/

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
/*
            List<Generation> generations = geminiResponseDTO.getCandidates().stream()
                    .map((candidate) -> candidate.getContent().getParts())
                    .flatMap(Collection::stream).map(ContentPart::getText)
                    .map(Generation::new).toList();
            return new ChatResponse(generations, null);*/

            log.error("对象{}", geminiResponseDTO);
            String finishReason = geminiResponseDTO.getCandidates().getFirst().getFinishReason();
            if(finishReason.equals("SAFETY")){
                return MessageResponse.builder().data(Message.builder().role("model")
                        .content("\n\n ``由于安全问题，该回答被中断！``")
                        .build()).code(GlobalErrorCodeConstants.SUCCESS.getCode()).build();
            }
            try {
                String text = geminiResponseDTO.getCandidates().getFirst().getContent().getParts().getFirst().getText();
                Message message = Message.builder().role("model")
                        .content(text)
                        .build();
                return MessageResponse.builder().data(message).code(GlobalErrorCodeConstants.SUCCESS.getCode()).build();
            } catch (Exception e) {
                log.error("对象{}出现异常{}", geminiResponseDTO, e.getMessage(), e);
                return MessageResponse.builder().data(null).code(GlobalErrorCodeConstants.SUCCESS.getCode()).build();
            }

        });
        return responseFlux;
    }

    @Override
    public Integer countToken(List<Message> messages, ModelDTO model) {
        List<Content> contents = messages.stream().filter(Objects::nonNull).map(
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
