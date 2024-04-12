package com.stonewu.aifusion.module.ai.controller.app;

import com.stonewu.aifusion.module.ai.api.google.dto.Content;
import com.stonewu.aifusion.module.ai.api.google.dto.ContentPart;
import com.stonewu.aifusion.module.ai.api.google.dto.GeminiRequestDTO;
import com.stonewu.aifusion.module.ai.api.google.dto.GeminiResponseDTO;
import com.stonewu.aifusion.module.ai.service.ai.GoogleAiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Collections;

@Tag(name = "app - 对话")
@RestController
@RequestMapping("/ai/gemini")
@Validated
public class GeminiController {

    @Resource
    private GoogleAiService googleAiService;

    @GetMapping("/chat")
    public Flux<GeminiResponseDTO> chat(String prompt) {
        Content content = Content.builder().parts(Collections.singletonList(ContentPart.builder().text(prompt).build())).build();
        GeminiRequestDTO requestDTO = GeminiRequestDTO.builder().contents(Collections.singletonList(content)).build();
        return googleAiService.chat(requestDTO, "gemini-pro", "***");
    }

}
