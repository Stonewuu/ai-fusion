package com.stonewu.aifusion.module.crm.controller.admin.contract.vo.contract;

import com.stonewu.aifusion.framework.common.validation.InEnum;
import com.stonewu.aifusion.module.crm.enums.permission.CrmPermissionLevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - CRM 合同转移 Request VO")
@Data
public class CrmContractTransferReqVO {

    @Schema(description = "合同编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10430")
    @NotNull(message = "联系人编号不能为空")
    private Long id;

    @Schema(description = "新负责人的用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10430")
    @NotNull(message = "新负责人的用户编号不能为空")
    private Long newOwnerUserId;

    @Schema(description = "老负责人加入团队后的权限级别", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @InEnum(value = CrmPermissionLevelEnum.class)
    private Integer oldOwnerPermissionLevel;

}
