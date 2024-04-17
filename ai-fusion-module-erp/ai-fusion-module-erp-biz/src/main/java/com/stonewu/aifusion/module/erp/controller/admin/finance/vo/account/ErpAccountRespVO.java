package com.stonewu.aifusion.module.erp.controller.admin.finance.vo.account;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.stonewu.aifusion.framework.excel.core.annotations.DictFormat;
import com.stonewu.aifusion.module.system.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - ERP 结算账户 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ErpAccountRespVO {

    @Schema(description = "结算账户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28684")
    @ExcelProperty("结算账户编号")
    private Long id;

    @Schema(description = "账户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("账户名称")
    private String name;

    @Schema(description = "账户编码", example = "A88")
    @ExcelProperty("账户编码")
    private String no;

    @Schema(description = "备注", example = "")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "开启状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("开启状态")
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "是否默认", example = "1")
    @ExcelProperty("是否默认")
    private Boolean defaultStatus;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}