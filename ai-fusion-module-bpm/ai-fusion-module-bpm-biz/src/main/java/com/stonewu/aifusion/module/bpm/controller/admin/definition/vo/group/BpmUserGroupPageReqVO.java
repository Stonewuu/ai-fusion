package com.stonewu.aifusion.module.bpm.controller.admin.definition.vo.group;

import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.common.util.date.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 用户组分页 Request VO")
@Data
public class BpmUserGroupPageReqVO extends PageParam {

    @Schema(description = "编号", example = "1024")
    private Long id;

    @Schema(description = "组名", example = "芋道")
    private String name;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

}
