package com.stonewu.aifusion.module.ai.controller.app;

import com.stonewu.aifusion.module.ai.api.google.dto.Content;
import com.stonewu.aifusion.module.ai.api.google.dto.ContentPart;
import com.stonewu.aifusion.module.ai.api.google.dto.GeminiRequestDTO;
import com.stonewu.aifusion.module.ai.api.google.dto.GeminiResponseDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import com.stonewu.aifusion.module.ai.service.ai.AiServiceProvider;
import com.stonewu.aifusion.module.ai.service.ai.GoogleAiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;

@Tag(name = "app - 对话")
@RestController
@RequestMapping("/ai")
@Validated
public class ChatController {

    @Resource
    private GoogleAiService googleAiService;

    private AiServiceProvider aiServiceProvider;

    @GetMapping("/chat")
    public Flux<Message> chat(Long assistantID, List<Message> messages) {

        return aiServiceProvider.chat(assistantID, messages);
    }

}
