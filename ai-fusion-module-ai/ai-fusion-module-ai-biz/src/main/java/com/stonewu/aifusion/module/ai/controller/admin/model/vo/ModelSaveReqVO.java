package com.stonewu.aifusion.module.ai.controller.admin.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.AssistantDO;

@Schema(description = "管理后台 - AI模型新增/修改 Request VO")
@Data
public class ModelSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32077")
    private Long id;

    @Schema(description = "模型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "模型名称不能为空")
    private String name;

    @Schema(description = "模型ApiKey")
    private String apiKey;

    @Schema(description = "英文名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "英文名称不能为空")
    private String modelName;

    @Schema(description = "模型类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "模型类型不能为空")
    private Integer modelType;

    @Schema(description = "模型价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "23526")
    @NotNull(message = "模型价格不能为空")
    private BigDecimal modelPrice;

    @Schema(description = "备注")
    private String remark;

}