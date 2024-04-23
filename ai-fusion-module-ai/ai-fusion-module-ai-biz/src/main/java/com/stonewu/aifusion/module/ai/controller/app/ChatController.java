package com.stonewu.aifusion.module.ai.controller.app;

import com.stonewu.aifusion.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.stonewu.aifusion.framework.common.pojo.CommonResult;
import com.stonewu.aifusion.framework.security.core.annotations.PreAuthenticated;
import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.google.dto.Content;
import com.stonewu.aifusion.module.ai.api.google.dto.ContentPart;
import com.stonewu.aifusion.module.ai.api.google.dto.GeminiRequestDTO;
import com.stonewu.aifusion.module.ai.api.google.dto.GeminiResponseDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import com.stonewu.aifusion.module.ai.service.ai.AiServiceProvider;
import com.stonewu.aifusion.module.ai.service.ai.GoogleAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.stonewu.aifusion.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 对话")
@RestController
@RequestMapping("/ai")
@Validated
@Slf4j
public class ChatController {

    @Resource
    private GoogleAiService googleAiService;

    private AiServiceProvider aiServiceProvider;

    @GetMapping("/chat")
    @Operation(summary = "流式对话")
    @PreAuthenticated
    public Flux<CommonResult<MessageResponse>> chat(Long assistantID, List<Message> messages) {
        Long loginUserId = getLoginUserId();


        Flux<MessageResponse> chat = aiServiceProvider.chat(assistantID, messages);
        if (chat == null) {
            return Flux.fromStream(Stream.of(CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "未找到匹配的模型")));
        }
        return chat.map(CommonResult::success);
    }

}
