package com.stonewu.aifusion.module.erp.controller.admin.product.vo.unit;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.stonewu.aifusion.framework.excel.core.annotations.DictFormat;
import com.stonewu.aifusion.module.system.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - ERP 产品单位 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ErpProductUnitRespVO {

    @Schema(description = "单位编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31254")
    @ExcelProperty("单位编号")
    private Long id;

    @Schema(description = "单位名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "aifusion")
    @ExcelProperty("单位名字")
    private String name;

    @Schema(description = "单位状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("单位状态")
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}