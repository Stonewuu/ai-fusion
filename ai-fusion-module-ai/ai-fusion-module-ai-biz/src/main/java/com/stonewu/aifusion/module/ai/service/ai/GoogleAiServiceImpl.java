package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.module.ai.api.google.dto.GeminiRequestDTO;
import com.stonewu.aifusion.module.ai.api.google.dto.GeminiResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class GoogleAiServiceImpl implements GoogleAiService {

    WebClient client = WebClient.builder().baseUrl("https://generativelanguage.googleapis.com/")
            .defaultHeader("Content-Type","application/json")
            .defaultHeader("Accept", "text/event-stream")
            .defaultHeader("Cache-Control", "no-cache")
            .defaultHeader("Connection", "keep-alive")
            .build();

    @Override
    public Flux<GeminiResponseDTO> chat(GeminiRequestDTO geminiRequestDTO, String model, String apiKey) {
        return client.post().uri("/v1beta/models/"+model+":streamGenerateContent?alt=sse&key="+apiKey)
                .bodyValue(geminiRequestDTO).retrieve().bodyToFlux(GeminiResponseDTO.class);
    }

}
