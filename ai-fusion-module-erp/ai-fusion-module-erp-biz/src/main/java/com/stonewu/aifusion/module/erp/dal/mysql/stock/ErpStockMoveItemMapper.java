package com.stonewu.aifusion.module.erp.dal.mysql.stock;

import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.module.erp.dal.dataobject.stock.ErpStockMoveItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * ERP 库存调拨单项 Mapper
 *
 *
 */
@Mapper
public interface ErpStockMoveItemMapper extends BaseMapperX<ErpStockMoveItemDO> {

    default List<ErpStockMoveItemDO> selectListByMoveId(Long moveId) {
        return selectList(ErpStockMoveItemDO::getMoveId, moveId);
    }

    default List<ErpStockMoveItemDO> selectListByMoveIds(Collection<Long> moveIds) {
        return selectList(ErpStockMoveItemDO::getMoveId, moveIds);
    }

    default int deleteByMoveId(Long moveId) {
        return delete(ErpStockMoveItemDO::getMoveId, moveId);
    }

}