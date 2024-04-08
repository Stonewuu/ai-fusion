package com.stonewu.aifusion.module.statistics.service.pay.bo;

import lombok.Data;

/**
 * 充值统计 Response BO
 */
@Data
public class RechargeSummaryRespBO {

    /**
     * 充值会员数量
     */
    private Integer rechargeUserCount;

    /**
     * 充值金额
     */
    private Integer rechargePrice;

}
