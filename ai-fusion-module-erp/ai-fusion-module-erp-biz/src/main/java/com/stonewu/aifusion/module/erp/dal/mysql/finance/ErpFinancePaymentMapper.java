package com.stonewu.aifusion.module.erp.dal.mysql.finance;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.framework.mybatis.core.query.MPJLambdaWrapperX;
import com.stonewu.aifusion.module.erp.controller.admin.finance.vo.payment.ErpFinancePaymentPageReqVO;
import com.stonewu.aifusion.module.erp.dal.dataobject.finance.ErpFinancePaymentDO;
import com.stonewu.aifusion.module.erp.dal.dataobject.finance.ErpFinancePaymentItemDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ERP 付款单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ErpFinancePaymentMapper extends BaseMapperX<ErpFinancePaymentDO> {

    default PageResult<ErpFinancePaymentDO> selectPage(ErpFinancePaymentPageReqVO reqVO) {
        MPJLambdaWrapperX<ErpFinancePaymentDO> query = new MPJLambdaWrapperX<ErpFinancePaymentDO>()
                .likeIfPresent(ErpFinancePaymentDO::getNo, reqVO.getNo())
                .betweenIfPresent(ErpFinancePaymentDO::getPaymentTime, reqVO.getPaymentTime())
                .eqIfPresent(ErpFinancePaymentDO::getSupplierId, reqVO.getSupplierId())
                .eqIfPresent(ErpFinancePaymentDO::getCreator, reqVO.getCreator())
                .eqIfPresent(ErpFinancePaymentDO::getFinanceUserId, reqVO.getFinanceUserId())
                .eqIfPresent(ErpFinancePaymentDO::getAccountId, reqVO.getAccountId())
                .eqIfPresent(ErpFinancePaymentDO::getStatus, reqVO.getStatus())
                .likeIfPresent(ErpFinancePaymentDO::getRemark, reqVO.getRemark())
                .orderByDesc(ErpFinancePaymentDO::getId);
        if (reqVO.getBizNo() != null) {
            query.leftJoin(ErpFinancePaymentItemDO.class, ErpFinancePaymentItemDO::getPaymentId, ErpFinancePaymentDO::getId)
                    .eq(reqVO.getBizNo() != null, ErpFinancePaymentItemDO::getBizNo, reqVO.getBizNo())
                    .groupBy(ErpFinancePaymentDO::getId); // 避免 1 对多查询，产生相同的 1
        }
        return selectJoinPage(reqVO, ErpFinancePaymentDO.class, query);
    }

    default int updateByIdAndStatus(Long id, Integer status, ErpFinancePaymentDO updateObj) {
        return update(updateObj, new LambdaUpdateWrapper<ErpFinancePaymentDO>()
                .eq(ErpFinancePaymentDO::getId, id).eq(ErpFinancePaymentDO::getStatus, status));
    }

    default ErpFinancePaymentDO selectByNo(String no) {
        return selectOne(ErpFinancePaymentDO::getNo, no);
    }

}