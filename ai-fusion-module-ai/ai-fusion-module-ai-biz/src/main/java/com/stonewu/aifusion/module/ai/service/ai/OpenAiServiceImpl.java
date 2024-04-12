package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OpenAiServiceImpl implements OpenAiService {
    WebClient client = WebClient.builder().baseUrl("https://api.openai.com/").build();

    @Override
    public void chat(OpenAiRequestDTO openAiRequestDTO, String apiKey) {
        client.post().uri("v1/chat/completions").header("Authorization", "Bearer " + apiKey)
                .bodyValue(openAiRequestDTO).retrieve().bodyToMono(String.class).subscribe(System.out::println);
    }

}
