package com.stonewu.aifusion.module.trade.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 订单改价 Request VO")
@Data
public class TradeOrderUpdatePriceReqVO {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "订单编号不能为空")
    private Long id;

    @Schema(description = "订单调价，单位：分。正数，加价；负数，减价", requiredMode = Schema.RequiredMode.REQUIRED, example = "-100")
    @NotNull(message = "订单调价价格不能为空")
    private Integer adjustPrice;

}
