package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import reactor.core.publisher.Flux;

import java.util.List;

public interface AiServiceProvider {
    Flux<MessageResponse> streamChat(Long assistantID, List<Message> messages);
}
