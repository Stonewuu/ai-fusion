package com.stonewu.aifusion.module.ai.api.openai.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiRequestDTO {

    private String model;

    private boolean stream;

    private List<Message> messages;

}
