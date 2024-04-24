package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.google.dto.Content;
import com.stonewu.aifusion.module.ai.api.google.dto.ContentPart;
import com.stonewu.aifusion.module.ai.api.google.dto.GeminiRequestDTO;
import com.stonewu.aifusion.module.ai.api.google.dto.GeminiResponseDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiResponseDTO;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.ModelDO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;

@Service("googleAiService")
public class GoogleAiServiceImpl implements AiService {

    WebClient client = WebClient.builder().baseUrl("https://generativelanguage.googleapis.com/")
            .defaultHeader("Content-Type","application/json")
            .defaultHeader("Accept", "text/event-stream")
            .defaultHeader("Cache-Control", "no-cache")
            .defaultHeader("Connection", "keep-alive")
            .build();


    @Override
    public Flux<MessageResponse> chat(List<Message> messages, ModelDO model) {

        // google gemini
        GeminiRequestDTO geminiRequestDTO = new GeminiRequestDTO();
        // 将messages转换成contents
        List<Content> contents = messages.stream().map(
                message ->  Content.builder().role(message.getRole())
                        .parts(Collections.singletonList(ContentPart.builder().text(message.getContent()).build()))
                        .build()
        ).toList();
        geminiRequestDTO.setContents(contents);
        Flux<GeminiResponseDTO> chat = client.post().uri("/v1beta/models/" + model.getModelName() + ":streamGenerateContent?alt=sse&key=" + model.getApiKey())
                .bodyValue(geminiRequestDTO).retrieve().bodyToFlux(GeminiResponseDTO.class);
        Flux<MessageResponse> responseFlux = chat.map(geminiResponseDTO -> {
            try {
                String text = geminiResponseDTO.getCandidates().getFirst().getContent().getParts().getFirst().getText();
                Message message = Message.builder().role("model")
                        .content(text)
                        .build();
                return MessageResponse.builder().message(message).code(GlobalErrorCodeConstants.SUCCESS.getCode()).build();
            }catch (Exception e){
                return MessageResponse.builder().message(null).code(GlobalErrorCodeConstants.SUCCESS.getCode()).build();
            }
        });
        return responseFlux;
    }
}
