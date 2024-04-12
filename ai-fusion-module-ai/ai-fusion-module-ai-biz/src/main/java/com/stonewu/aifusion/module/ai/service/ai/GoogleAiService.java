package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.module.ai.api.google.dto.GeminiRequestDTO;
import com.stonewu.aifusion.module.ai.api.google.dto.GeminiResponseDTO;
import reactor.core.publisher.Flux;

public interface GoogleAiService {
    Flux<GeminiResponseDTO> chat(GeminiRequestDTO openAiRequestDTO, String model, String apiKey);
}
