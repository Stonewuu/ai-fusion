package com.stonewu.aifusion.module.ai.api.openai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usage {

    private int promptTokens;

    private int completionTokens;

    private int totalTokens;
}
