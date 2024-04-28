package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.ai.dto.ModelDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiRequestDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service("openAiService")
public class OpenAiServiceImpl implements AiService {
    WebClient client = WebClient.builder().baseUrl("https://api.openai.com/").build();

    @Override
    public Flux<MessageResponse> streamChat(List<Message> messages, ModelDTO model) {
        OpenAiRequestDTO openAiRequestDTO = OpenAiRequestDTO.builder()
                .messages(messages)
                .stream(true)
                .model(model.getModelName())
                .build();
        Flux<OpenAiResponseDTO> chat = client.post().uri("v1/chat/completions").header("Authorization", "Bearer " + model.getApiKey())
                .bodyValue(openAiRequestDTO).retrieve().bodyToFlux(OpenAiResponseDTO.class);

        Flux<MessageResponse> responseFlux = chat.map(openAiResponseDTO -> {
            String content = openAiResponseDTO.getChoices().getFirst().getMessage().getContent();
            Message message = Message.builder().role("assistant")
                    .content(content).build();
            MessageResponse build = MessageResponse.builder().data(message).code(GlobalErrorCodeConstants.SUCCESS.getCode()).build();
            return build;
        });
        return responseFlux;
    }

    @Override
    public Integer countToken(List<Message> messages, ModelDTO model) {
        return null;
    }

}
