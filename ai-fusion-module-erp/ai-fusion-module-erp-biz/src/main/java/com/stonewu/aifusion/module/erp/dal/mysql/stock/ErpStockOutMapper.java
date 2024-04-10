package com.stonewu.aifusion.module.erp.dal.mysql.stock;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.framework.mybatis.core.query.MPJLambdaWrapperX;
import com.stonewu.aifusion.module.erp.controller.admin.stock.vo.out.ErpStockOutPageReqVO;
import com.stonewu.aifusion.module.erp.dal.dataobject.stock.ErpStockOutDO;
import com.stonewu.aifusion.module.erp.dal.dataobject.stock.ErpStockOutItemDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ERP 其它出库单 Mapper
 *
 *
 */
@Mapper
public interface ErpStockOutMapper extends BaseMapperX<ErpStockOutDO> {

    default PageResult<ErpStockOutDO> selectPage(ErpStockOutPageReqVO reqVO) {
        MPJLambdaWrapperX<ErpStockOutDO> query = new MPJLambdaWrapperX<ErpStockOutDO>()
                .likeIfPresent(ErpStockOutDO::getNo, reqVO.getNo())
                .eqIfPresent(ErpStockOutDO::getCustomerId, reqVO.getCustomerId())
                .betweenIfPresent(ErpStockOutDO::getOutTime, reqVO.getOutTime())
                .eqIfPresent(ErpStockOutDO::getStatus, reqVO.getStatus())
                .likeIfPresent(ErpStockOutDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ErpStockOutDO::getCreator, reqVO.getCreator())
                .orderByDesc(ErpStockOutDO::getId);
        if (reqVO.getWarehouseId() != null || reqVO.getProductId() != null) {
            query.leftJoin(ErpStockOutItemDO.class, ErpStockOutItemDO::getOutId, ErpStockOutDO::getId)
                    .eq(reqVO.getWarehouseId() != null, ErpStockOutItemDO::getWarehouseId, reqVO.getWarehouseId())
                    .eq(reqVO.getProductId() != null, ErpStockOutItemDO::getProductId, reqVO.getProductId())
                    .groupBy(ErpStockOutDO::getId); // 避免 1 对多查询，产生相同的 1
        }
        return selectJoinPage(reqVO, ErpStockOutDO.class, query);
    }

    default int updateByIdAndStatus(Long id, Integer status, ErpStockOutDO updateObj) {
        return update(updateObj, new LambdaUpdateWrapper<ErpStockOutDO>()
                .eq(ErpStockOutDO::getId, id).eq(ErpStockOutDO::getStatus, status));
    }

    default ErpStockOutDO selectByNo(String no) {
        return selectOne(ErpStockOutDO::getNo, no);
    }

}