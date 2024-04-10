package com.stonewu.aifusion.module.pay.api.notify.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付单的通知 Request DTO
 *
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderNotifyReqDTO {

    /**
     * 商户订单编号
     */
    @NotEmpty(message = "商户订单号不能为空")
    private String merchantOrderId;

    /**
     * 支付订单编号
     */
    @NotNull(message = "支付订单编号不能为空")
    private Long payOrderId;

}
