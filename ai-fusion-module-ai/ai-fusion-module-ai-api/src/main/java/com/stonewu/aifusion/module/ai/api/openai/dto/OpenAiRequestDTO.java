package com.stonewu.aifusion.module.ai.api.openai.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Builder
public class OpenAiRequestDTO {

    private String model;

    private boolean stream;

    private List<Message> messages;

}
