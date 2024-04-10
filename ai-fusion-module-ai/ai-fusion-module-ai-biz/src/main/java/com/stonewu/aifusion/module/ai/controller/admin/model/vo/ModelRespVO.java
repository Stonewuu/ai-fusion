package com.stonewu.aifusion.module.ai.controller.admin.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.stonewu.aifusion.framework.excel.core.annotations.DictFormat;
import com.stonewu.aifusion.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - AI模型 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ModelRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "23418")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "模型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "gpt-4模型")
    @ExcelProperty("模型名称")
    private String name;

    @Schema(description = "ApiKey")
    @ExcelProperty("ApiKey")
    private String apiKey;

    @Schema(description = "官方名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "gpt-4")
    @ExcelProperty("官方名称")
    private String modelName;

    @Schema(description = "模型类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "模型类型", converter = DictConvert.class)
    @DictFormat("ai_model_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer modelType;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}