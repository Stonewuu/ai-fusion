package com.stonewu.aifusion.module.erp.controller.admin.stock.vo.warehouse;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.stonewu.aifusion.framework.excel.core.annotations.DictFormat;
import com.stonewu.aifusion.framework.excel.core.convert.DictConvert;
import com.stonewu.aifusion.module.system.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - ERP 仓库 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ErpWarehouseRespVO {

    @Schema(description = "仓库编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11614")
    @ExcelProperty("仓库编号")
    private Long id;

    @Schema(description = "仓库名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("仓库名称")
    private String name;

    @Schema(description = "仓库地址", example = "上海陆家嘴")
    @ExcelProperty("仓库地址")
    private String address;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @ExcelProperty("排序")
    private Long sort;

    @Schema(description = "备注", example = "")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "负责人", example = "AI-Fusion")
    @ExcelProperty("负责人")
    private String principal;

    @Schema(description = "仓储费，单位：元", example = "13973")
    @ExcelProperty("仓储费，单位：元")
    private BigDecimal warehousePrice;

    @Schema(description = "搬运费，单位：元", example = "9903")
    @ExcelProperty("搬运费，单位：元")
    private BigDecimal truckagePrice;

    @Schema(description = "开启状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "开启状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @Schema(description = "是否默认", example = "1")
    @ExcelProperty("是否默认")
    private Boolean defaultStatus;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}