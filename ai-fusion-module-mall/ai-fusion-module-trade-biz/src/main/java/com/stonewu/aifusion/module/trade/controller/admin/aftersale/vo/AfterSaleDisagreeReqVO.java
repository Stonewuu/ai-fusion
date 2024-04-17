package com.stonewu.aifusion.module.trade.controller.admin.aftersale.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 交易售后拒绝 Request VO")
@Data
public class AfterSaleDisagreeReqVO {

    @Schema(description = "售后编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "售后编号不能为空")
    private Long id;

    @Schema(description = "审批备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "")
    @NotEmpty(message = "审批备注不能为空")
    private String auditReason;

}
