package com.stonewu.aifusion.module.crm.dal.mysql.contact;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.stonewu.aifusion.framework.mybatis.core.query.MPJLambdaWrapperX;
import com.stonewu.aifusion.module.crm.controller.admin.contact.vo.CrmContactPageReqVO;
import com.stonewu.aifusion.module.crm.dal.dataobject.contact.CrmContactDO;
import com.stonewu.aifusion.module.crm.enums.common.CrmBizTypeEnum;
import com.stonewu.aifusion.module.crm.util.CrmPermissionUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * CRM 联系人 Mapper
 *
 * 
 */
@Mapper
public interface CrmContactMapper extends BaseMapperX<CrmContactDO> {

    default int updateOwnerUserIdByCustomerId(Long customerId, Long ownerUserId) {
        return update(new LambdaUpdateWrapper<CrmContactDO>()
                .eq(CrmContactDO::getCustomerId, customerId)
                .set(CrmContactDO::getOwnerUserId, ownerUserId));
    }

    default PageResult<CrmContactDO> selectPageByCustomerId(CrmContactPageReqVO pageVO) {
        return selectPage(pageVO, new LambdaQueryWrapperX<CrmContactDO>()
                .eq(CrmContactDO::getCustomerId, pageVO.getCustomerId()) // 指定客户编号
                .likeIfPresent(CrmContactDO::getName, pageVO.getName())
                .eqIfPresent(CrmContactDO::getMobile, pageVO.getMobile())
                .eqIfPresent(CrmContactDO::getTelephone, pageVO.getTelephone())
                .eqIfPresent(CrmContactDO::getEmail, pageVO.getEmail())
                .eqIfPresent(CrmContactDO::getQq, pageVO.getQq())
                .eqIfPresent(CrmContactDO::getWechat, pageVO.getWechat())
                .orderByDesc(CrmContactDO::getId));
    }

    default PageResult<CrmContactDO> selectPageByBusinessId(CrmContactPageReqVO pageVO, Collection<Long> ids) {
        return selectPage(pageVO, new LambdaQueryWrapperX<CrmContactDO>()
                .in(CrmContactDO::getId, ids) // 指定联系人编号
                .likeIfPresent(CrmContactDO::getName, pageVO.getName())
                .eqIfPresent(CrmContactDO::getMobile, pageVO.getMobile())
                .eqIfPresent(CrmContactDO::getTelephone, pageVO.getTelephone())
                .eqIfPresent(CrmContactDO::getEmail, pageVO.getEmail())
                .eqIfPresent(CrmContactDO::getQq, pageVO.getQq())
                .eqIfPresent(CrmContactDO::getWechat, pageVO.getWechat())
                .orderByDesc(CrmContactDO::getId));
    }

    default PageResult<CrmContactDO> selectPage(CrmContactPageReqVO pageReqVO, Long userId) {
        MPJLambdaWrapperX<CrmContactDO> query = new MPJLambdaWrapperX<>();
        // 拼接数据权限的查询条件
        CrmPermissionUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_CONTACT.getType(),
                CrmContactDO::getId, userId, pageReqVO.getSceneType(), Boolean.FALSE);
        // 拼接自身的查询条件
        query.selectAll(CrmContactDO.class)
                .likeIfPresent(CrmContactDO::getName, pageReqVO.getName())
                .eqIfPresent(CrmContactDO::getMobile, pageReqVO.getMobile())
                .eqIfPresent(CrmContactDO::getTelephone, pageReqVO.getTelephone())
                .eqIfPresent(CrmContactDO::getEmail, pageReqVO.getEmail())
                .eqIfPresent(CrmContactDO::getQq, pageReqVO.getQq())
                .eqIfPresent(CrmContactDO::getWechat, pageReqVO.getWechat())
                .orderByDesc(CrmContactDO::getId);
        return selectJoinPage(pageReqVO, CrmContactDO.class, query);
    }

    default List<CrmContactDO> selectListByCustomerId(Long customerId) {
        return selectList(CrmContactDO::getCustomerId, customerId);
    }

}
