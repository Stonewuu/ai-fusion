package com.stonewu.aifusion.module.pay.api.notify.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 转账单的通知 Request DTO
 *
 * @author jason
 */
@Data
public class PayTransferNotifyReqDTO {

    /**
     * 商户转账单号
     */
    @NotEmpty(message = "商户转账单号不能为空")
    private String merchantTransferId;

    /**
     * 转账订单编号
     */
    @NotNull(message = "转账订单编号不能为空")
    private Long payTransferId;
}
