package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.ai.dto.ModelDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.ModelDO;
import reactor.core.publisher.Flux;

import java.util.List;

public interface AiService {

    Flux<MessageResponse> streamChat(List<Message> messages, ModelDTO model);
    Integer countToken(List<Message> messages, ModelDTO model);
}
