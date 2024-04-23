package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.google.dto.Content;
import com.stonewu.aifusion.module.ai.api.google.dto.ContentPart;
import com.stonewu.aifusion.module.ai.api.google.dto.GeminiRequestDTO;
import com.stonewu.aifusion.module.ai.api.google.dto.GeminiResponseDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiRequestDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiResponseDTO;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.AssistantDO;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.ModelDO;
import com.stonewu.aifusion.module.ai.enums.ErrorCodeConstants;
import com.stonewu.aifusion.module.ai.service.model.ModelService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
public class AiServiceProviderImpl implements AiServiceProvider {
    @Resource
    private ModelService modelService;
    @Resource
    private OpenAiService openAiService;
    @Resource
    private GoogleAiService googleAiService;

    @Override
    public Flux<MessageResponse> chat(Long assistantID, List<Message> messages){
        AssistantDO assistant = modelService.getAssistant(assistantID);
        if (assistant == null) {
            return Flux.fromStream(Stream.of(MessageResponse.builder().code(ErrorCodeConstants.ASSISTANT_NOT_EXISTS.getCode()).build()));
        }
        ModelDO model = modelService.getModel(assistant.getModelId());
        if (model == null) {
            return Flux.fromStream(Stream.of(MessageResponse.builder().code(ErrorCodeConstants.MODEL_NOT_EXISTS.getCode()).build()));
        }
        String apiKey = model.getApiKey();
        Integer modelType = model.getModelType();
        if (modelType == 1) {
            // openai
            OpenAiRequestDTO openAiRequestDTO = OpenAiRequestDTO.builder()
                    .messages(messages)
                    .model(model.getModelName())
                    .build();
            Flux<OpenAiResponseDTO> chat = openAiService.chat(openAiRequestDTO, apiKey);
            return chat.map(openAiResponseDTO -> {
                Message message = Message.builder().role("assistant")
                        .content(openAiResponseDTO.getChoices().getFirst().getMessage().getContent()).build();
                return MessageResponse.builder().message(message).code(GlobalErrorCodeConstants.SUCCESS.getCode()).build();
            });

        } else if (modelType == 2) {
            // google gemini
            GeminiRequestDTO geminiRequestDTO = new GeminiRequestDTO();
            // 将messages转换成contents
            List<Content> contents = messages.stream().map(
                    message ->  Content.builder().role(message.getRole())
                                .parts(Collections.singletonList(ContentPart.builder().text(message.getContent()).build()))
                                .build()
            ).toList();
            geminiRequestDTO.setContents(contents);
            Flux<GeminiResponseDTO> chat = googleAiService.chat(geminiRequestDTO, model.getModelName(), apiKey);
            return chat.map(geminiResponseDTO -> {
                Message message = Message.builder().role("model")
                        .content(geminiResponseDTO.getCandidates().getFirst().getContent().getParts().getFirst().getText())
                        .build();
                return MessageResponse.builder().message(message).code(GlobalErrorCodeConstants.SUCCESS.getCode()).build();
            });
        }
        return Flux.fromStream(Stream.of(MessageResponse.builder().code(ErrorCodeConstants.MODEL_NO_SUCH_TYPE.getCode()).build()));
    }

}
