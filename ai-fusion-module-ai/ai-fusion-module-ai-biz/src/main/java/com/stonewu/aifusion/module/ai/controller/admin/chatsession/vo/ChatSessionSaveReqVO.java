package com.stonewu.aifusion.module.ai.controller.admin.chatsession.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import com.stonewu.aifusion.module.ai.dal.dataobject.chatsession.ChatRecordDO;

@Schema(description = "管理后台 - 对话记录新增/修改 Request VO")
@Data
public class ChatSessionSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1957")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21970")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "助手ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10633")
    @NotNull(message = "助手ID不能为空")
    private Long assistantId;

    @Schema(description = "备注")
    private String remark;

}