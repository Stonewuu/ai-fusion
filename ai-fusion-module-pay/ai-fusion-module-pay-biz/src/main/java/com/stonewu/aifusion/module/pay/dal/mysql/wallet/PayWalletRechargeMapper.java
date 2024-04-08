package com.stonewu.aifusion.module.pay.dal.mysql.wallet;

import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.mybatis.core.mapper.BaseMapperX;
import com.stonewu.aifusion.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.stonewu.aifusion.module.pay.dal.dataobject.wallet.PayWalletRechargeDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayWalletRechargeMapper extends BaseMapperX<PayWalletRechargeDO> {

    default int updateByIdAndPaid(Long id, boolean wherePayStatus, PayWalletRechargeDO updateObj) {
        return update(updateObj, new LambdaQueryWrapperX<PayWalletRechargeDO>()
                .eq(PayWalletRechargeDO::getId, id).eq(PayWalletRechargeDO::getPayStatus, wherePayStatus));
    }

    default int updateByIdAndRefunded(Long id, Integer whereRefundStatus, PayWalletRechargeDO updateObj) {
        return update(updateObj, new LambdaQueryWrapperX<PayWalletRechargeDO>()
                .eq(PayWalletRechargeDO::getId, id).eq(PayWalletRechargeDO::getRefundStatus, whereRefundStatus));
    }

    default PageResult<PayWalletRechargeDO> selectPage(PageParam pageReqVO, Long walletId, Boolean payStatus) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<PayWalletRechargeDO>()
                .eq(PayWalletRechargeDO::getWalletId, walletId)
                .eq(PayWalletRechargeDO::getPayStatus, payStatus)
                .orderByDesc(PayWalletRechargeDO::getId));
    }

}