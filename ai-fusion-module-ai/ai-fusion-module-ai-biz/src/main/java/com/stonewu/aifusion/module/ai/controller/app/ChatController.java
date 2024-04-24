package com.stonewu.aifusion.module.ai.controller.app;

import com.stonewu.aifusion.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.stonewu.aifusion.framework.security.core.annotations.PreAuthenticated;
import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import com.stonewu.aifusion.module.ai.service.ai.AiServiceProvider;
import com.stonewu.aifusion.module.member.api.point.MemberPointApi;
import com.stonewu.aifusion.module.member.api.user.MemberUserApi;
import com.stonewu.aifusion.module.member.api.user.dto.MemberUserRespDTO;
import com.stonewu.aifusion.module.member.enums.point.MemberPointBizTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.stonewu.aifusion.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 对话")
@RestController
@RequestMapping("/ai")
@Validated
@Slf4j
public class ChatController {

    @Resource
    private AiServiceProvider aiServiceProvider;

    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private MemberPointApi memberPointApi;


    @PostMapping("/chat")
    @Operation(summary = "流式对话")
    @PreAuthenticated
    public Flux<MessageResponse> chat(Long assistantID, @RequestBody List<Message> messages) {
        Long loginId = getLoginUserId();
        MemberUserRespDTO user = memberUserApi.getUser(loginId);
        Flux<MessageResponse> chat = aiServiceProvider.streamChat(assistantID, messages);
        if (chat == null) {
            return Flux.fromStream(Stream.of(MessageResponse.builder().code(GlobalErrorCodeConstants.BAD_REQUEST.getCode()).build()));
        }
        AtomicInteger i = new AtomicInteger();

        return chat.doOnEach(messageResponse -> {
            MessageResponse response = messageResponse.get();
            if (response != null) {
                String content = response.getMessage().getContent();
                // 根据content计算token
                i.addAndGet(content.length());
                log.info("当前累计长度：" + i);

            }
        }).doAfterTerminate(() -> {
            log.info("累计content长度" + i);
//            memberPointApi.reducePoint(loginId, 1, MemberPointBizTypeEnum.TOKEN_USE.getType(), "chat");
        });
    }

}
