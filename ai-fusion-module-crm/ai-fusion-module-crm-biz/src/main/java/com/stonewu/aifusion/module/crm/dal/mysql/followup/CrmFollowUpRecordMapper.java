package com.stonewu.aifusion.module.crm.dal.mysql.followup;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.stonewu.aifusion.module.crm.controller.admin.followup.vo.CrmFollowUpRecordPageReqVO;
import com.stonewu.aifusion.module.crm.dal.dataobject.followup.CrmFollowUpRecordDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * 跟进记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CrmFollowUpRecordMapper extends BaseMapperX<CrmFollowUpRecordDO> {

    default PageResult<CrmFollowUpRecordDO> selectPage(CrmFollowUpRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmFollowUpRecordDO>()
                .eqIfPresent(CrmFollowUpRecordDO::getBizType, reqVO.getBizType())
                .eqIfPresent(CrmFollowUpRecordDO::getBizId, reqVO.getBizId())
                .orderByDesc(CrmFollowUpRecordDO::getId));
    }

    default void deleteByBiz(Integer bizType, Long bizId) {
        delete(new LambdaQueryWrapperX<CrmFollowUpRecordDO>()
                .eq(CrmFollowUpRecordDO::getBizType, bizType)
                .eq(CrmFollowUpRecordDO::getBizId, bizId));
    }

    default List<CrmFollowUpRecordDO> selectListByBiz(Integer bizType, Collection<Long> bizIds) {
        return selectList(new LambdaQueryWrapperX<CrmFollowUpRecordDO>()
                .eq(CrmFollowUpRecordDO::getBizType, bizType)
                .in(CrmFollowUpRecordDO::getBizId, bizIds));
    }

}