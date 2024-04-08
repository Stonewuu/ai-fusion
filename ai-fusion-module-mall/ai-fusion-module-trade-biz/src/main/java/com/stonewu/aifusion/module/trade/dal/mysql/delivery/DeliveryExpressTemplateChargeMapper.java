package com.stonewu.aifusion.module.trade.dal.mysql.delivery;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.module.trade.dal.dataobject.delivery.DeliveryExpressTemplateChargeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface DeliveryExpressTemplateChargeMapper extends BaseMapperX<DeliveryExpressTemplateChargeDO> {

    default List<DeliveryExpressTemplateChargeDO> selectListByTemplateId(Long templateId){
        return selectList(new LambdaQueryWrapper<DeliveryExpressTemplateChargeDO>()
                .eq(DeliveryExpressTemplateChargeDO::getTemplateId, templateId));
    }

    default int deleteByTemplateId(Long templateId){
       return delete(new LambdaQueryWrapper<DeliveryExpressTemplateChargeDO>()
               .eq(DeliveryExpressTemplateChargeDO::getTemplateId, templateId));
    }

    default List<DeliveryExpressTemplateChargeDO> selectByTemplateIds(Collection<Long> templateIds) {
        return selectList(DeliveryExpressTemplateChargeDO::getTemplateId, templateIds);
    }

}




