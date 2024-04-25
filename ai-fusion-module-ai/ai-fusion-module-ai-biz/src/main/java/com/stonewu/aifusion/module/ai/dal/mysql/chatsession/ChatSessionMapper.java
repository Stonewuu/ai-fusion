package com.stonewu.aifusion.module.ai.dal.mysql.chatsession;

import java.util.*;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.module.ai.dal.dataobject.chatsession.ChatSessionDO;
import org.apache.ibatis.annotations.Mapper;
import com.stonewu.aifusion.module.ai.controller.admin.chatsession.vo.*;

/**
 * 对话记录 Mapper
 *
 * @author AiFusion
 */
@Mapper
public interface ChatSessionMapper extends BaseMapperX<ChatSessionDO> {

    default PageResult<ChatSessionDO> selectPage(ChatSessionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ChatSessionDO>()
                .eqIfPresent(ChatSessionDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ChatSessionDO::getAssistantId, reqVO.getAssistantId())
                .betweenIfPresent(ChatSessionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ChatSessionDO::getId));
    }

}