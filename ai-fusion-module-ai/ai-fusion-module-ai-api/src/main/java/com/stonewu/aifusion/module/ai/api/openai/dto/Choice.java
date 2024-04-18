package com.stonewu.aifusion.module.ai.api.openai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Choice {

    private int index;

    private Message message;

    private String finishReason;

}
