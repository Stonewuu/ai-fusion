package com.stonewu.aifusion.module.ai.controller.admin.model.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.stonewu.aifusion.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.stonewu.aifusion.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - AI模型分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ModelPageReqVO extends PageParam {

    @Schema(description = "模型名称", example = "gpt-4模型")
    private String name;

    @Schema(description = "ApiKey")
    private String apiKey;

    @Schema(description = "官方名称", example = "gpt-4")
    private String modelName;

    @Schema(description = "模型类型", example = "1")
    private Integer modelType;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}