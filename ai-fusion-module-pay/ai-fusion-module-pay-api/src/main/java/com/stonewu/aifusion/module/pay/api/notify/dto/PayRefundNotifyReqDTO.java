package com.stonewu.aifusion.module.pay.api.notify.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 退款单的通知 Request DTO
 *
 * @author 芋道源码
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayRefundNotifyReqDTO {

    /**
     * 商户退款单编号
     */
    @NotEmpty(message = "商户退款单编号不能为空")
    private String merchantOrderId;

    /**
     * 支付退款编号
     */
    @NotNull(message = "支付退款编号不能为空")
    private Long payRefundId;

}
