package com.stonewu.aifusion.module.trade.service.message.bo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 订单发货时通知创建 Req BO
 *
 * @author HUIHUI
 */
@Data
public class TradeOrderMessageWhenDeliveryOrderReqBO {

    /**
     * 订单编号
     */
    @NotNull(message = "订单编号不能为空")
    private Long orderId;
    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    private Long userId;
    /**
     * 消息
     */
    @NotEmpty(message = "发送消息不能为空")
    private String message;

}
