package com.stonewu.aifusion.module.ai.controller.admin.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.AssistantDO;

@Schema(description = "管理后台 - AI模型新增/修改 Request VO")
@Data
public class ModelSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21093")
    private Long id;

    @Schema(description = "模型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "AI-Fusion")
    @NotEmpty(message = "模型名称不能为空")
    private String name;

    @Schema(description = "模型ApiKey")
    private String apiKey;

    @Schema(description = "官方英文称", requiredMode = Schema.RequiredMode.REQUIRED, example = "AI-Fusion")
    @NotEmpty(message = "官方英文称不能为空")
    private String modelName;

    @Schema(description = "模型类型	1.openai	2.google", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "模型类型	1.openai	2.google不能为空")
    private Integer modelType;

    @Schema(description = "备注")
    private String remark;

}