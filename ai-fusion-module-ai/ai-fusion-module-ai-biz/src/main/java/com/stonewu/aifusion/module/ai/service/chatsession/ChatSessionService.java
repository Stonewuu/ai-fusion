package com.stonewu.aifusion.module.ai.service.chatsession;

import java.util.*;
import jakarta.validation.*;
import com.stonewu.aifusion.module.ai.controller.admin.chatsession.vo.*;
import com.stonewu.aifusion.module.ai.dal.dataobject.chatsession.ChatSessionDO;
import com.stonewu.aifusion.module.ai.dal.dataobject.chatsession.ChatRecordDO;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.pojo.PageParam;

/**
 * 对话记录 Service 接口
 *
 * @author AiFusion
 */
public interface ChatSessionService {

    /**
     * 创建对话记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createChatSession(@Valid ChatSessionSaveReqVO createReqVO);

    /**
     * 更新对话记录
     *
     * @param updateReqVO 更新信息
     */
    void updateChatSession(@Valid ChatSessionSaveReqVO updateReqVO);

    /**
     * 删除对话记录
     *
     * @param id 编号
     */
    void deleteChatSession(Long id);

    /**
     * 获得对话记录
     *
     * @param id 编号
     * @return 对话记录
     */
    ChatSessionDO getChatSession(Long id);

    /**
     * 获得对话记录分页
     *
     * @param pageReqVO 分页查询
     * @return 对话记录分页
     */
    PageResult<ChatSessionDO> getChatSessionPage(ChatSessionPageReqVO pageReqVO);

    // ==================== 子表（对话记录） ====================

    /**
     * 获得对话记录分页
     *
     * @param pageReqVO 分页查询
     * @param sessionId 会话ID
     * @return 对话记录分页
     */
    PageResult<ChatRecordDO> getChatRecordPage(PageParam pageReqVO, Long sessionId);

    /**
     * 创建对话记录
     *
     * @param chatRecord 创建信息
     * @return 编号
     */
    Long createChatRecord(@Valid ChatRecordDO chatRecord);

    /**
     * 更新对话记录
     *
     * @param chatRecord 更新信息
     */
    void updateChatRecord(@Valid ChatRecordDO chatRecord);

    /**
     * 删除对话记录
     *
     * @param id 编号
     */
    void deleteChatRecord(Long id);

	/**
	 * 获得对话记录
	 *
	 * @param id 编号
     * @return 对话记录
	 */
    ChatRecordDO getChatRecord(Long id);

}