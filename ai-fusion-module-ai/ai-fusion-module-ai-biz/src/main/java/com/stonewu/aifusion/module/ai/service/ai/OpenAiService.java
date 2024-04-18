package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiRequestDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiResponseDTO;
import reactor.core.publisher.Flux;

public interface OpenAiService {
    Flux<OpenAiResponseDTO> chat(OpenAiRequestDTO openAiRequestDTO, String apiKey);
}
