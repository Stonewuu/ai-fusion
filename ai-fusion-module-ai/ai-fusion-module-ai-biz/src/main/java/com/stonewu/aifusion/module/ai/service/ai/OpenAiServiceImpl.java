package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiRequestDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class OpenAiServiceImpl implements OpenAiService {
    WebClient client = WebClient.builder().baseUrl("https://api.openai.com/").build();

    @Override
    public Flux<OpenAiResponseDTO> chat(OpenAiRequestDTO openAiRequestDTO, String apiKey) {
        return client.post().uri("v1/chat/completions").header("Authorization", "Bearer " + apiKey)
                .bodyValue(openAiRequestDTO).retrieve().bodyToFlux(OpenAiResponseDTO.class);
    }

}
