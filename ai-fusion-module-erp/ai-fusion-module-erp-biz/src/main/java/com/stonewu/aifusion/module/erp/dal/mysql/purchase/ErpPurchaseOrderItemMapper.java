package com.stonewu.aifusion.module.erp.dal.mysql.purchase;

import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.module.erp.dal.dataobject.purchase.ErpPurchaseOrderItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * ERP 采购订单明项目 Mapper
 *
 *
 */
@Mapper
public interface ErpPurchaseOrderItemMapper extends BaseMapperX<ErpPurchaseOrderItemDO> {

    default List<ErpPurchaseOrderItemDO> selectListByOrderId(Long orderId) {
        return selectList(ErpPurchaseOrderItemDO::getOrderId, orderId);
    }

    default List<ErpPurchaseOrderItemDO> selectListByOrderIds(Collection<Long> orderIds) {
        return selectList(ErpPurchaseOrderItemDO::getOrderId, orderIds);
    }

    default int deleteByOrderId(Long orderId) {
        return delete(ErpPurchaseOrderItemDO::getOrderId, orderId);
    }

}