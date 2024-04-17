package com.stonewu.aifusion.module.ai.dal.mysql.model;

import java.util.*;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.AssistantDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * AI助手 Mapper
 *
 * @author AiFusion
 */
@Mapper
public interface AssistantMapper extends BaseMapperX<AssistantDO> {

    default PageResult<AssistantDO> selectPage(PageParam reqVO, Long modelId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AssistantDO>()
            .eq(AssistantDO::getModelId, modelId)
            .orderByDesc(AssistantDO::getId));
    }

    default int deleteByModelId(Long modelId) {
        return delete(AssistantDO::getModelId, modelId);
    }

}