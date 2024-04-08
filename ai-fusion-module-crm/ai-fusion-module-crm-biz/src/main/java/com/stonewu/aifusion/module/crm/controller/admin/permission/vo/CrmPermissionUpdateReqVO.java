package com.stonewu.aifusion.module.crm.controller.admin.permission.vo;

import com.stonewu.aifusion.framework.common.validation.InEnum;
import com.stonewu.aifusion.module.crm.enums.common.CrmBizTypeEnum;
import com.stonewu.aifusion.module.crm.enums.permission.CrmPermissionLevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - CRM 数据权限更新 Request VO")
@Data
public class CrmPermissionUpdateReqVO {

    @Schema(description = "数据权限编号列表", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1,2]")
    @NotNull(message = "数据权限编号列表不能为空")
    private List<Long> ids;

    @Schema(description = "Crm 类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @InEnum(CrmBizTypeEnum.class)
    @NotNull(message = "Crm 类型不能为空")
    private Integer bizType;

    @Schema(description = "Crm 类型数据编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Crm 类型数据编号不能为空")
    private Long bizId;

    @Schema(description = "权限级别", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @InEnum(CrmPermissionLevelEnum.class)
    @NotNull(message = "权限级别不能为空")
    private Integer level;

}
