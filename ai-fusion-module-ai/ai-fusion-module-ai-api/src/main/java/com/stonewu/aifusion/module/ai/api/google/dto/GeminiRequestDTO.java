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
public class GeminiRequestDTO {

    private List<Content> contents;

    private List<SafetySetting> safetySettings;

    private GenerationConfig generationConfig;


}
