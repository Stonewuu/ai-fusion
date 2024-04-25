package com.stonewu.aifusion.module.ai.service.chatsession;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.stonewu.aifusion.module.ai.controller.admin.chatsession.vo.*;
import com.stonewu.aifusion.module.ai.dal.dataobject.chatsession.ChatSessionDO;
import com.stonewu.aifusion.module.ai.dal.dataobject.chatsession.ChatRecordDO;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.common.util.object.BeanUtils;

import com.stonewu.aifusion.module.ai.dal.mysql.chatsession.ChatSessionMapper;
import com.stonewu.aifusion.module.ai.dal.mysql.chatsession.ChatRecordMapper;

import static com.stonewu.aifusion.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.stonewu.aifusion.module.ai.enums.ErrorCodeConstants.*;

/**
 * 对话记录 Service 实现类
 *
 * @author AiFusion
 */
@Service
@Validated
public class ChatSessionServiceImpl implements ChatSessionService {

    @Resource
    private ChatSessionMapper chatSessionMapper;
    @Resource
    private ChatRecordMapper chatRecordMapper;

    @Override
    public Long createChatSession(ChatSessionSaveReqVO createReqVO) {
        // 插入
        ChatSessionDO chatSession = BeanUtils.toBean(createReqVO, ChatSessionDO.class);
        chatSessionMapper.insert(chatSession);
        // 返回
        return chatSession.getId();
    }

    @Override
    public void updateChatSession(ChatSessionSaveReqVO updateReqVO) {
        // 校验存在
        validateChatSessionExists(updateReqVO.getId());
        // 更新
        ChatSessionDO updateObj = BeanUtils.toBean(updateReqVO, ChatSessionDO.class);
        chatSessionMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteChatSession(Long id) {
        // 校验存在
        validateChatSessionExists(id);
        // 删除
        chatSessionMapper.deleteById(id);

        // 删除子表
        deleteChatRecordBySessionId(id);
    }

    private void validateChatSessionExists(Long id) {
        if (chatSessionMapper.selectById(id) == null) {
            throw exception(CHAT_SESSION_NOT_EXISTS);
        }
    }

    @Override
    public ChatSessionDO getChatSession(Long id) {
        return chatSessionMapper.selectById(id);
    }

    @Override
    public PageResult<ChatSessionDO> getChatSessionPage(ChatSessionPageReqVO pageReqVO) {
        return chatSessionMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（对话记录） ====================

    @Override
    public PageResult<ChatRecordDO> getChatRecordPage(PageParam pageReqVO, Long sessionId) {
        return chatRecordMapper.selectPage(pageReqVO, sessionId);
    }

    @Override
    public Long createChatRecord(ChatRecordDO chatRecord) {
        chatRecordMapper.insert(chatRecord);
        return chatRecord.getId();
    }

    @Override
    public void updateChatRecord(ChatRecordDO chatRecord) {
        // 校验存在
        validateChatRecordExists(chatRecord.getId());
        // 更新
        chatRecordMapper.updateById(chatRecord);
    }

    @Override
    public void deleteChatRecord(Long id) {
        // 校验存在
        validateChatRecordExists(id);
        // 删除
        chatRecordMapper.deleteById(id);
    }

    @Override
    public ChatRecordDO getChatRecord(Long id) {
        return chatRecordMapper.selectById(id);
    }

    private void validateChatRecordExists(Long id) {
        if (chatRecordMapper.selectById(id) == null) {
            throw exception(CHAT_RECORD_NOT_EXISTS);
        }
    }

    private void deleteChatRecordBySessionId(Long sessionId) {
        chatRecordMapper.deleteBySessionId(sessionId);
    }

}