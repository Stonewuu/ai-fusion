package com.stonewu.aifusion.module.ai.api.openai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiResponseDTO {

    private String id;

    private String object;

    private Instant created;

    private String model;

    private String systemFingerprint;

    private List<Choice> choices;

    private Usage usage;


}
