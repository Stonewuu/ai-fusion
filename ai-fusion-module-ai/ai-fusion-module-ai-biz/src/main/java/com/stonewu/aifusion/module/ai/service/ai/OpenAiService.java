package com.stonewu.aifusion.module.ai.service.ai;

import com.stonewu.aifusion.module.ai.api.openai.dto.OpenAiRequestDTO;

public interface OpenAiService {
    void chat(OpenAiRequestDTO openAiRequestDTO, String apiKey);
}
