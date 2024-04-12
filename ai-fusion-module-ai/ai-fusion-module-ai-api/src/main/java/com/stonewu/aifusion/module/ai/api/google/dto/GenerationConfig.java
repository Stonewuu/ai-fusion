package com.stonewu.aifusion.module.ai.api.google.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerationConfig {

    private List<String> stopSequences;

    private double temperature;

    private int maxOutputTokens;

    private double topP;

    private int topK;
}
