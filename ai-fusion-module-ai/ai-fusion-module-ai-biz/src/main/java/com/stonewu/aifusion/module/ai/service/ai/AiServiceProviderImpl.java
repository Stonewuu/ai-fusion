package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.ai.dto.ModelDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import com.stonewu.aifusion.module.ai.enums.ErrorCodeConstants;
import com.stonewu.aifusion.module.ai.service.model.ModelService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
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
    public Flux<MessageResponse> streamChat(ModelDTO model, List<Message> messages) {
        Integer modelType = model.getModelType();
        return getAiService(modelType).streamChat(messages, model);
    }

    @Override
    public Integer countTokens(ModelDTO model, List<Message> messages){
        Integer modelType = model.getModelType();
        Integer tokenCount = getAiService(modelType).countToken(messages, model);
        return tokenCount;
    }

    private AiService getAiService(int modelType) {
        return switch (modelType) {
            case 1 -> openAiService;
            case 2 -> googleAiService;
            default -> fallbackAiService;
        };
    }

}
