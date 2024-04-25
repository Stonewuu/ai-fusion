package com.stonewu.aifusion.module.ai.controller.admin.chatsession.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 对话记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ChatSessionRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1957")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21970")
    @ExcelProperty("用户编号")
    private Long userId;

    @Schema(description = "助手ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10633")
    @ExcelProperty("助手ID")
    private Long assistantId;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}