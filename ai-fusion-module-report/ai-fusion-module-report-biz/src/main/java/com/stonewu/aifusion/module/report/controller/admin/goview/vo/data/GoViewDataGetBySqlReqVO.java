package com.stonewu.aifusion.module.report.controller.admin.goview.vo.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Schema(description = "管理后台 - GoView 使用 SQL 查询数据 Request VO")
@Data
public class GoViewDataGetBySqlReqVO {

    @Schema(description = "SQL 语句", requiredMode = Schema.RequiredMode.REQUIRED, example = "SELECT * FROM user")
    @NotEmpty(message = "SQL 语句不能为空")
    private String sql;

}
