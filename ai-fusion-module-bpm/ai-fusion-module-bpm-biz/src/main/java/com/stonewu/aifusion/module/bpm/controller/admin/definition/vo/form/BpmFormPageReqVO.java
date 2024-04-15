package com.stonewu.aifusion.module.bpm.controller.admin.definition.vo.form;

import com.stonewu.aifusion.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 动态表单分页 Request VO")
@Data
public class BpmFormPageReqVO extends PageParam {

    @Schema(description = "表单名称", example = "AI-Fusion")
    private String name;

}
