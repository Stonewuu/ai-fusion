package com.stonewu.aifusion.module.trade.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 订单备注 Request VO")
@Data
public class TradeOrderRemarkReqVO {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "订单编号不能为空")
    private Long id;

    @Schema(description = "商家备注", example = "一下")
    @NotEmpty(message = "订单备注不能为空")
    private String remark;

}
