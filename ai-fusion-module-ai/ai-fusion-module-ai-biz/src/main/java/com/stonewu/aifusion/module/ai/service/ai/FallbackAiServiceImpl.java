package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiRequestDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiResponseDTO;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.ModelDO;
import com.stonewu.aifusion.module.ai.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

@Service("fallbackAiService")
public class FallbackAiServiceImpl implements AiService {
    @Override
    public Flux<MessageResponse> chat(List<Message> messages, ModelDO model) {
        return Flux.fromStream(Stream.of(MessageResponse.builder().code(ErrorCodeConstants.MODEL_NO_SUCH_TYPE.getCode()).build()));
    }

}
