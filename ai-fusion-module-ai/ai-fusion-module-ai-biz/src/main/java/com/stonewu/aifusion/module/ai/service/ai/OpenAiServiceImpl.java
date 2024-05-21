package com.stonewu.aifusion.module.ai.service.ai;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import com.stonewu.aifusion.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.stonewu.aifusion.framework.common.util.json.JsonUtils;
import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.ai.dto.ModelDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiRequestDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Predicate;

@Service("openAiService")
@Slf4j
public class OpenAiServiceImpl implements AiService {
    private static String url = "https://api.openai.com/";
    private static final Predicate<String> SSE_DONE_PREDICATE = "[DONE]"::equals;
    WebClient client = WebClient.builder()
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Accept", "text/event-stream")
            .defaultHeader("Cache-Control", "no-cache")
            .defaultHeader("Connection", "keep-alive")
            .build();

    @Override
    public Flux<MessageResponse> streamChat(List<Message> messages, ModelDTO model) {

        String baseUrl = model.getBaseApi();
        if (StringUtils.isEmpty(baseUrl)) {
            baseUrl = url;
        }
/*

        var openAiApi = new OpenAiApi(baseUrl, model.getApiKey());
        var openAiChatOptions = OpenAiChatOptions.builder()
                .withModel(model.getModelName())
//                .withTemperature(0.4F)
//                .withMaxTokens(200)
                .build();
        var chatClient = new OpenAiChatClient(openAiApi, openAiChatOptions);
        List<org.springframework.ai.chat.messages.Message> messageList = messages.stream().map(message -> {
            if (message.getRole().equals("user")) {
                return new UserMessage(message.getContent());
            } else if (message.getRole().equals("assistant")) {
                return new AssistantMessage(message.getContent());
            }
            return new AssistantMessage(message.getContent());
        }).collect(Collectors.toList());
        ChatResponse call = chatClient.call(new Prompt(messageList));
        System.out.println(call);
        Flux<ChatResponse> hello = chatClient.stream(new Prompt(messageList));
*/


        OpenAiRequestDTO openAiRequestDTO = OpenAiRequestDTO.builder()
                .messages(messages)
                .stream(true)
                .model(model.getModelName())
                .build();
        Flux<OpenAiResponseDTO> chat = client.post().uri(baseUrl + "/chat/completions").header("Authorization", "Bearer " + model.getApiKey())
                .bodyValue(openAiRequestDTO).retrieve().bodyToFlux(String.class)
                // cancels the flux stream after the "[DONE]" is received.
                .takeUntil(SSE_DONE_PREDICATE)
                .filter(SSE_DONE_PREDICATE.negate()).map(json -> {
                    OpenAiResponseDTO openAiResponseDTO = JsonUtils.parseObject(json, OpenAiResponseDTO.class);
                    return openAiResponseDTO;
                });

        Flux<MessageResponse> responseFlux = chat.map(openAiResponseDTO -> {
            MessageResponse build;
            if (openAiResponseDTO.getChoices().getFirst().getDelta() != null) {
                String content = openAiResponseDTO.getChoices().getFirst().getDelta().getContent();
                Message message = Message.builder().role("assistant")
                        .content(content).build();
                build = MessageResponse.builder().data(message).code(GlobalErrorCodeConstants.SUCCESS.getCode()).build();
            } else {
                build = MessageResponse.builder().data(null).code(GlobalErrorCodeConstants.SUCCESS.getCode()).build();
            }
            return build;
        });
        return responseFlux;
    }

    @Override
    public Integer countToken(List<Message> messages, ModelDTO model) {
        StringBuilder sb = new StringBuilder();
        messages.stream().forEach(message -> sb.append(message.getContent()));
        return Base64Encoder.encode(sb.toString()).length();
    }

}
