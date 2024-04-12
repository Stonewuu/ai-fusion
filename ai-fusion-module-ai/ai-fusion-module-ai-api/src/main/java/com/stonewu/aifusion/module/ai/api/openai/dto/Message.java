package com.stonewu.aifusion.module.ai.api.openai.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    private String role;
    private String content;
}
