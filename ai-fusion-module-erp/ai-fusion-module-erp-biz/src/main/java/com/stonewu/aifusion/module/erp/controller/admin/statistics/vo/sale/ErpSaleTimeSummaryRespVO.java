package com.stonewu.aifusion.module.erp.controller.admin.statistics.vo.sale;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - ERP 销售某个时间段的统计 Response VO")
@Data
public class ErpSaleTimeSummaryRespVO {

    @Schema(description = "时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "2022-03")
    private String time;

    @Schema(description = "销售金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private BigDecimal price;

}
