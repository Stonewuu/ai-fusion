package com.stonewu.aifusion.module.ai.controller.admin.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.stonewu.aifusion.framework.excel.core.annotations.DictFormat;
import com.stonewu.aifusion.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - AI模型 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ModelRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32077")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "模型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("模型名称")
    private String name;

    @Schema(description = "模型ApiKey")
    @ExcelProperty("模型ApiKey")
    private String apiKey;

    @Schema(description = "英文名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("英文名称")
    private String modelName;

    @Schema(description = "模型类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "模型类型", converter = DictConvert.class)
    @DictFormat("ai_model_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer modelType;

    @Schema(description = "模型价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "23526")
    @ExcelProperty("模型价格")
    private BigDecimal modelPrice;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}