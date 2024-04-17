package com.stonewu.aifusion.module.ai.dal.mysql.model;

import java.util.*;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.ModelDO;
import org.apache.ibatis.annotations.Mapper;
import com.stonewu.aifusion.module.ai.controller.admin.model.vo.*;

/**
 * AI模型 Mapper
 *
 * @author AiFusion
 */
@Mapper
public interface ModelMapper extends BaseMapperX<ModelDO> {

    default PageResult<ModelDO> selectPage(ModelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ModelDO>()
                .likeIfPresent(ModelDO::getName, reqVO.getName())
                .likeIfPresent(ModelDO::getModelName, reqVO.getModelName())
                .eqIfPresent(ModelDO::getModelType, reqVO.getModelType())
                .betweenIfPresent(ModelDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ModelDO::getId));
    }

}