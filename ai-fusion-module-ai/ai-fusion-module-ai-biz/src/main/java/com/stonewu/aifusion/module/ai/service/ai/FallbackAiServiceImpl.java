package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.ai.dto.ModelDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service("fallbackAiService")
public class FallbackAiServiceImpl implements AiService {
    @Override
    public Flux<MessageResponse> streamChat(List<Message> messages, ModelDTO model) {
        return Flux.empty();
    }

    @Override
    public Integer countToken(List<Message> messages, ModelDTO model) {
        return null;
    }

}
