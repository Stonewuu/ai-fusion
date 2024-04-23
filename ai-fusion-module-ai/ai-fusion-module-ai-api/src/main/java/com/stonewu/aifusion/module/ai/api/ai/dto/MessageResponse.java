package com.stonewu.aifusion.module.ai.api.ai.dto;


import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private Integer code;

    private Message message;
}
