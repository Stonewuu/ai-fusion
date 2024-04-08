package com.stonewu.aifusion.module.crm.controller.admin.receivable.vo.receivable;

import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.common.validation.InEnum;
import com.stonewu.aifusion.module.crm.enums.common.CrmAuditStatusEnum;
import com.stonewu.aifusion.module.crm.enums.common.CrmSceneTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - CRM 回款分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmReceivablePageReqVO extends PageParam {

    @Schema(description = "回款编号")
    private String no;

    @Schema(description = "回款计划编号", example = "31177")
    private Long planId;

    @Schema(description = "客户编号", example = "4963")
    private Long customerId;

    @Schema(description = "合同编号", example = "4963")
    private Long contractId;

    @Schema(description = "场景类型", example = "1")
    @InEnum(CrmSceneTypeEnum.class)
    private Integer sceneType; // 场景类型，为 null 时则表示全部

    @Schema(description = "审批状态", example = "20")
    @InEnum(CrmAuditStatusEnum.class)
    private Integer auditStatus;

}
