package com.stonewu.aifusion.module.ai.controller.app;

import com.stonewu.aifusion.framework.common.util.object.BeanUtils;
import com.stonewu.aifusion.framework.security.core.annotations.PreAuthenticated;
import com.stonewu.aifusion.module.ai.api.ai.dto.MessageResponse;
import com.stonewu.aifusion.module.ai.api.ai.dto.ModelDTO;
import com.stonewu.aifusion.module.ai.api.openai.dto.Message;
import com.stonewu.aifusion.module.ai.controller.admin.chatsession.vo.ChatSessionSaveReqVO;
import com.stonewu.aifusion.module.ai.dal.dataobject.chatsession.ChatRecordDO;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.AssistantDO;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.ModelDO;
import com.stonewu.aifusion.module.ai.enums.ChatSenderType;
import com.stonewu.aifusion.module.ai.service.ai.AiServiceProvider;
import com.stonewu.aifusion.module.ai.service.chatsession.ChatSessionService;
import com.stonewu.aifusion.module.ai.service.model.ModelService;
import com.stonewu.aifusion.module.member.api.point.MemberPointApi;
import com.stonewu.aifusion.module.member.api.user.MemberUserApi;
import com.stonewu.aifusion.module.member.api.user.dto.MemberUserRespDTO;
import com.stonewu.aifusion.module.member.enums.point.MemberPointBizTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.stonewu.aifusion.framework.security.core.util.SecurityFrameworkUtils.*;
import static com.stonewu.aifusion.module.ai.enums.ErrorCodeConstants.*;
import static com.stonewu.aifusion.module.member.enums.ErrorCodeConstants.USER_POINT_NOT_ENOUGH;

@Tag(name = "用户 APP - 对话")
@RestController
@RequestMapping("/ai")
@Validated
@Slf4j
public class ChatController {

    @Resource
    private AiServiceProvider aiServiceProvider;
    @Resource
    private ModelService modelService;

    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private MemberPointApi memberPointApi;
    @Resource
    private ChatSessionService chatSessionService;


    @PostMapping("/chat")
    @Operation(summary = "流式对话")
    @PreAuthenticated
    public Flux<MessageResponse> chat(@RequestParam("assistantID") Long assistantID,
                                      @RequestParam("chatSessionID") @Nullable Long chatSessionID,
                                      @RequestBody List<Message> oldMessages,
                                      @RequestParam("message") String message) {
        Long loginId = getLoginUserId();
        MemberUserRespDTO user = memberUserApi.getUser(loginId);
        if (user.getPoint() <= 0) {
            return Flux.fromStream(Stream.of(MessageResponse.builder()
                    .code(USER_POINT_NOT_ENOUGH.getCode()).msg(USER_POINT_NOT_ENOUGH.getMsg()).build()));
        }
        AssistantDO assistant = modelService.getAssistant(assistantID);
        if (assistant == null) {
            return Flux.fromStream(Stream.of(MessageResponse.builder().code(ASSISTANT_NOT_EXISTS.getCode()).msg(ASSISTANT_NOT_EXISTS.getMsg()).build()));
        }
        ModelDO model = modelService.getModel(assistant.getModelId());
        if (model == null) {
            return Flux.fromStream(Stream.of(MessageResponse.builder().code(MODEL_NOT_EXISTS.getCode()).msg(MODEL_NOT_EXISTS.getMsg()).build()));
        }
        if (StringUtils.isEmpty(model.getApiKey())) {
            return Flux.fromStream(Stream.of(MessageResponse.builder().code(MODEL_NO_KEYS.getCode()).msg(MODEL_NO_KEYS.getMsg()).build()));
        }
        ModelDTO modelDTO = BeanUtils.toBean(model, ModelDTO.class);

        Integer requestToken = aiServiceProvider.countTokens(modelDTO, oldMessages);
        // 创建对话
        chatSessionID = createChatSession(assistantID, chatSessionID, loginId);
        // 保存对话记录
        saveChatRecord(chatSessionID, message, requestToken, ChatSenderType.USER.getCode(), loginId);

        Flux<MessageResponse> chat = aiServiceProvider.streamChat(modelDTO, oldMessages);

        List<Message> messagesResponse = new ArrayList<>();
        Long finalChatSessionID = chatSessionID;
        StringBuilder outputMsg = new StringBuilder();
        return chat.map(messageResponse -> {
            messageResponse.setChatSessionId(finalChatSessionID);
            Message messageSection = messageResponse.getData();
            // 根据content计算token
            messagesResponse.add(messageSection);
            if (messageSection != null) {
                outputMsg.append(messageSection.getContent());
            }
            return messageResponse;
        }).doOnComplete(() -> {
            Integer responseToken = aiServiceProvider.countTokens(modelDTO, messagesResponse);
            // 积分计算
            BigDecimal modelPrice = model.getModelPrice();
            BigDecimal responseCost = modelPrice.multiply(BigDecimal.valueOf(responseToken).divide(BigDecimal.valueOf(100)));
            BigDecimal requestCost = modelPrice.multiply(BigDecimal.valueOf(requestToken).divide(BigDecimal.valueOf(100)));
            Long recordId = saveChatRecord(finalChatSessionID, outputMsg.toString(), responseToken, ChatSenderType.MODEL.getCode(), loginId);
            memberPointApi.reducePoint(loginId,
                    requestCost.add(responseCost).round(new MathContext(1, RoundingMode.UP)).intValue(),
                    MemberPointBizTypeEnum.TOKEN_USE.getType(), recordId.toString(), true);
        });
    }

    private Long createChatSession(Long assistantID, Long chatSessionID, Long loginId) {
        if (chatSessionID == null) {
            ChatSessionSaveReqVO chatSessionSaveReqVO = new ChatSessionSaveReqVO();
            chatSessionSaveReqVO.setUserId(loginId);
            chatSessionSaveReqVO.setAssistantId(assistantID);
            chatSessionID = chatSessionService.createChatSession(chatSessionSaveReqVO);
        }
        return chatSessionID;
    }

    private Long saveChatRecord(Long chatSessionID, String message, int tokenCount, Integer code, Long loginId) {
        ChatRecordDO chatRecordDO = new ChatRecordDO();
        chatRecordDO.setContent(message);
        chatRecordDO.setTokenCount(tokenCount);
        chatRecordDO.setSender(code);
        chatRecordDO.setUserId(loginId);
        chatRecordDO.setSessionId(chatSessionID);
        Long recordId = chatSessionService.createChatRecord(chatRecordDO);
        return recordId;
    }

}
