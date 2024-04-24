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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Service
public class AiServiceProviderImpl implements AiServiceProvider {
    @Resource
    private ModelService modelService;
    @Resource(name = "openAiService")
    private AiService openAiService;
    @Resource(name = "googleAiService")
    private AiService googleAiService;
    @Resource(name = "fallbackAiService")
    private AiService fallbackAiService;

    @Override
    public Flux<MessageResponse> streamChat(Long assistantID, List<Message> messages) {
        AssistantDO assistant = modelService.getAssistant(assistantID);
        if (assistant == null) {
            return Flux.fromStream(Stream.of(MessageResponse.builder().code(ErrorCodeConstants.ASSISTANT_NOT_EXISTS.getCode()).build()));
        }
        ModelDO model = modelService.getModel(assistant.getModelId());
        if (model == null) {
            return Flux.fromStream(Stream.of(MessageResponse.builder().code(ErrorCodeConstants.MODEL_NOT_EXISTS.getCode()).build()));
        }
        String apiKey = model.getApiKey();
        if (StringUtils.isEmpty(apiKey)) {
            return Flux.fromStream(Stream.of(MessageResponse.builder().code(ErrorCodeConstants.MODEL_NO_KEYS.getCode()).build()));
        }
        Integer modelType = model.getModelType();
        return getAiService(modelType).chat(messages, model);
    }

    private AiService getAiService(int modelType) {
        return switch (modelType) {
            case 1 -> openAiService;
            case 2 -> googleAiService;
            default -> fallbackAiService;
        };
    }

}
