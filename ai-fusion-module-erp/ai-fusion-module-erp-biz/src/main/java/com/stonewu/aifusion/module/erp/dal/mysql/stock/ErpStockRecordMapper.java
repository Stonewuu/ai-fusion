package com.stonewu.aifusion.module.erp.dal.mysql.stock;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.stonewu.aifusion.module.erp.controller.admin.stock.vo.record.ErpStockRecordPageReqVO;
import com.stonewu.aifusion.module.erp.dal.dataobject.stock.ErpStockRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ERP 产品库存明细 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ErpStockRecordMapper extends BaseMapperX<ErpStockRecordDO> {

    default PageResult<ErpStockRecordDO> selectPage(ErpStockRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ErpStockRecordDO>()
                .eqIfPresent(ErpStockRecordDO::getProductId, reqVO.getProductId())
                .eqIfPresent(ErpStockRecordDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ErpStockRecordDO::getBizType, reqVO.getBizType())
                .likeIfPresent(ErpStockRecordDO::getBizNo, reqVO.getBizNo())
                .betweenIfPresent(ErpStockRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ErpStockRecordDO::getId));
    }

}