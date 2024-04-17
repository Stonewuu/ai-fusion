package com.stonewu.aifusion.module.erp.controller.admin.finance.vo.account;

import com.stonewu.aifusion.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - ERP 结算账户分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ErpAccountPageReqVO extends PageParam {

    @Schema(description = "账户编码", example = "A88")
    private String no;

    @Schema(description = "账户名称", example = "张三")
    private String name;

    @Schema(description = "备注", example = "")
    private String remark;

}