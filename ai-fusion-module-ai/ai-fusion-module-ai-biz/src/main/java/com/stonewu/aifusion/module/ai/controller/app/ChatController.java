package com.stonewu.aifusion.module.ai.controller.app;

import com.stonewu.aifusion.framework.common.exception.enums.GlobalErrorCodeConstants;
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
import com.stonewu.aifusion.module.ai.enums.ErrorCodeConstants;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.stonewu.aifusion.framework.security.core.util.SecurityFrameworkUtils.*;

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
        AssistantDO assistant = modelService.getAssistant(assistantID);
        if (assistant == null) {
            return Flux.fromStream(Stream.of(MessageResponse.builder().code(ErrorCodeConstants.ASSISTANT_NOT_EXISTS.getCode()).build()));
        }
        ModelDO model = modelService.getModel(assistant.getModelId());
        if (model == null) {
            return Flux.fromStream(Stream.of(MessageResponse.builder().code(ErrorCodeConstants.MODEL_NOT_EXISTS.getCode()).build()));
        }
        if(chatSessionID == null){
            ChatSessionSaveReqVO chatSessionSaveReqVO = new ChatSessionSaveReqVO();
            chatSessionSaveReqVO.setUserId(loginId);
            chatSessionSaveReqVO.setAssistantId(assistantID);
            chatSessionID = chatSessionService.createChatSession(chatSessionSaveReqVO);
        }
        ModelDTO modelDTO = BeanUtils.toBean(model, ModelDTO.class);

        Integer requestToken = aiServiceProvider.countTokens(modelDTO, oldMessages);
        // 保存对话记录
        saveChatRecord(chatSessionID, message,requestToken, ChatSenderType.USER.getCode(), loginId);

        Flux<MessageResponse> chat = aiServiceProvider.streamChat(modelDTO, oldMessages);
        if (chat == null) {
            return Flux.fromStream(Stream.of(MessageResponse.builder().code(GlobalErrorCodeConstants.BAD_REQUEST.getCode()).build()));
        }

        List<Message> messagesResponse = new ArrayList<>();
        Long finalChatSessionID = chatSessionID;
        StringBuilder outputMsg = new StringBuilder();
        return chat.map(messageResponse -> {
            messageResponse.setChatSessionId(finalChatSessionID);
            Message messageSection = messageResponse.getMessage();
            // 根据content计算token
            messagesResponse.add(messageSection);
            outputMsg.append(messageSection.getContent());
            return messageResponse;
        }).doOnComplete(() -> {
            Integer responseToken = aiServiceProvider.countTokens(modelDTO, messagesResponse);
            // 积分计算
            BigDecimal modelPrice = model.getModelPrice();
            BigDecimal reponseCost = modelPrice.multiply(BigDecimal.valueOf(responseToken).divide(BigDecimal.valueOf(1000)));
            BigDecimal requestCost = modelPrice.multiply(BigDecimal.valueOf(requestToken).divide(BigDecimal.valueOf(1000)));
            saveChatRecord(finalChatSessionID, outputMsg.toString(),responseToken, ChatSenderType.MODEL.getCode(), loginId);
            memberPointApi.reducePoint(loginId, requestCost.add(reponseCost).intValue(), MemberPointBizTypeEnum.TOKEN_USE.getType(), "chat");
        });
    }

    private void saveChatRecord(Long chatSessionID, String message, int tokenCount, Integer code, Long loginId) {
        ChatRecordDO chatRecordDO = new ChatRecordDO();
        chatRecordDO.setContent(message);
        chatRecordDO.setTokenCount(tokenCount);
        chatRecordDO.setSender(code);
        chatRecordDO.setUserId(loginId);
        chatRecordDO.setSessionId(chatSessionID);
        chatSessionService.createChatRecord(chatRecordDO);
    }

}
