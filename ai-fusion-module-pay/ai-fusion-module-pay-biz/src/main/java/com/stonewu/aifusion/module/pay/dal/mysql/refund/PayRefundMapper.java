package com.stonewu.aifusion.module.pay.dal.mysql.refund;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.stonewu.aifusion.module.pay.controller.admin.refund.vo.PayRefundExportReqVO;
import com.stonewu.aifusion.module.pay.controller.admin.refund.vo.PayRefundPageReqVO;
import com.stonewu.aifusion.module.pay.dal.dataobject.refund.PayRefundDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayRefundMapper extends BaseMapperX<PayRefundDO> {

    default Long selectCountByAppId(Long appId) {
        return selectCount(PayRefundDO::getAppId, appId);
    }

    default  PayRefundDO selectByAppIdAndMerchantRefundId(Long appId, String merchantRefundId) {
        return selectOne(new LambdaQueryWrapperX<PayRefundDO>()
                .eq(PayRefundDO::getAppId, appId)
                .eq(PayRefundDO::getMerchantRefundId, merchantRefundId));
    }

    default Long selectCountByAppIdAndOrderId(Long appId, Long orderId, Integer status) {
        return selectCount(new LambdaQueryWrapperX<PayRefundDO>()
                .eq(PayRefundDO::getAppId, appId)
                .eq(PayRefundDO::getOrderId, orderId)
                .eq(PayRefundDO::getStatus, status));
    }

    default  PayRefundDO selectByAppIdAndNo(Long appId, String no) {
        return selectOne(new LambdaQueryWrapperX<PayRefundDO>()
                .eq(PayRefundDO::getAppId, appId)
                .eq(PayRefundDO::getNo, no));
    }

    default PayRefundDO selectByNo(String no) {
        return selectOne(PayRefundDO::getNo, no);
    }

    default int updateByIdAndStatus(Long id, Integer status, PayRefundDO update) {
        return update(update, new LambdaQueryWrapper<PayRefundDO>()
                .eq(PayRefundDO::getId, id).eq(PayRefundDO::getStatus, status));
    }

    default PageResult<PayRefundDO> selectPage(PayRefundPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayRefundDO>()
                .eqIfPresent(PayRefundDO::getAppId, reqVO.getAppId())
                .eqIfPresent(PayRefundDO::getChannelCode, reqVO.getChannelCode())
                .likeIfPresent(PayRefundDO::getMerchantOrderId, reqVO.getMerchantOrderId())
                .likeIfPresent(PayRefundDO::getMerchantRefundId, reqVO.getMerchantRefundId())
                .likeIfPresent(PayRefundDO::getChannelOrderNo, reqVO.getChannelOrderNo())
                .likeIfPresent(PayRefundDO::getChannelRefundNo, reqVO.getChannelRefundNo())
                .eqIfPresent(PayRefundDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PayRefundDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayRefundDO::getId));
    }

    default List<PayRefundDO> selectList(PayRefundExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PayRefundDO>()
                .eqIfPresent(PayRefundDO::getAppId, reqVO.getAppId())
                .eqIfPresent(PayRefundDO::getChannelCode, reqVO.getChannelCode())
                .likeIfPresent(PayRefundDO::getMerchantOrderId, reqVO.getMerchantOrderId())
                .likeIfPresent(PayRefundDO::getMerchantRefundId, reqVO.getMerchantRefundId())
                .likeIfPresent(PayRefundDO::getChannelOrderNo, reqVO.getChannelOrderNo())
                .likeIfPresent(PayRefundDO::getChannelRefundNo, reqVO.getChannelRefundNo())
                .eqIfPresent(PayRefundDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PayRefundDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayRefundDO::getId));
    }

    default List<PayRefundDO> selectListByStatus(Integer status) {
        return selectList(PayRefundDO::getStatus, status);
    }
}
