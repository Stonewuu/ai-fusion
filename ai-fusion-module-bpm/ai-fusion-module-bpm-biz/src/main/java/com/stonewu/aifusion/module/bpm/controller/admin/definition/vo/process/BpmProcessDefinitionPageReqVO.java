package com.stonewu.aifusion.module.bpm.controller.admin.definition.vo.process;

import com.stonewu.aifusion.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 流程定义分页 Request VO")
@Data
public class BpmProcessDefinitionPageReqVO extends PageParam {

    @Schema(description = "标识-精准匹配", example = "process1641042089407")
    private String key;

}
