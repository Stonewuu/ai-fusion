package com.stonewu.aifusion.module.ai.dal.mysql.chatsession;

import java.util.*;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.module.ai.dal.dataobject.chatsession.ChatRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 对话记录 Mapper
 *
 * @author AiFusion
 */
@Mapper
public interface ChatRecordMapper extends BaseMapperX<ChatRecordDO> {

    default PageResult<ChatRecordDO> selectPage(PageParam reqVO, Long sessionId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ChatRecordDO>()
            .eq(ChatRecordDO::getSessionId, sessionId)
            .orderByDesc(ChatRecordDO::getId));
    }

    default int deleteBySessionId(Long sessionId) {
        return delete(ChatRecordDO::getSessionId, sessionId);
    }

}