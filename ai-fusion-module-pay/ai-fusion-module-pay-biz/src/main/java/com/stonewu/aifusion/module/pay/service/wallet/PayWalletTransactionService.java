package com.stonewu.aifusion.module.pay.service.wallet;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.module.pay.controller.admin.wallet.vo.transaction.PayWalletTransactionPageReqVO;
import com.stonewu.aifusion.module.pay.controller.app.wallet.vo.transaction.AppPayWalletTransactionPageReqVO;
import com.stonewu.aifusion.module.pay.controller.app.wallet.vo.transaction.AppPayWalletTransactionSummaryRespVO;
import com.stonewu.aifusion.module.pay.dal.dataobject.wallet.PayWalletTransactionDO;
import com.stonewu.aifusion.module.pay.enums.wallet.PayWalletBizTypeEnum;
import com.stonewu.aifusion.module.pay.service.wallet.bo.WalletTransactionCreateReqBO;
import jakarta.validation.Valid;

import java.time.LocalDateTime;

/**
 * 钱包余额流水 Service 接口
 *
 * @author jason
 */
public interface PayWalletTransactionService {

    /**
     * 查询钱包余额流水分页
     *
     * @param userId   用户编号
     * @param userType 用户类型
     * @param pageVO   分页查询参数
     */
    PageResult<PayWalletTransactionDO> getWalletTransactionPage(Long userId, Integer userType,
                                                                AppPayWalletTransactionPageReqVO pageVO);

    /**
     * 查询钱包余额流水分页
     *
     * @param pageVO   分页查询参数
     */
    PageResult<PayWalletTransactionDO> getWalletTransactionPage(PayWalletTransactionPageReqVO pageVO);

    /**
     * 新增钱包余额流水
     *
     * @param bo 创建钱包流水 bo
     * @return 新建的钱包 do
     */
    PayWalletTransactionDO createWalletTransaction(@Valid WalletTransactionCreateReqBO bo);

    /**
     * 根据 no，获取钱包余流水
     *
     * @param no 流水号
     */
    PayWalletTransactionDO getWalletTransactionByNo(String no);

    /**
     * 获取钱包流水
     *
     * @param bizId 业务编号
     * @param type  业务类型
     * @return 钱包流水
     */
    PayWalletTransactionDO getWalletTransaction(String bizId, PayWalletBizTypeEnum type);

    /**
     * 获得钱包流水统计
     *
     * @param userId 用户编号
     * @param userType 用户类型
     * @param createTime 时间段
     * @return 钱包流水统计
     */
    AppPayWalletTransactionSummaryRespVO getWalletTransactionSummary(Long userId, Integer userType,
                                                                     LocalDateTime[] createTime);

}
