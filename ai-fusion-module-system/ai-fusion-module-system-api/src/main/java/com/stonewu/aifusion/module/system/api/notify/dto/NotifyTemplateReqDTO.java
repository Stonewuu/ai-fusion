package com.stonewu.aifusion.module.system.api.notify.dto;

import com.stonewu.aifusion.framework.common.enums.CommonStatusEnum;
import com.stonewu.aifusion.framework.common.validation.InEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotifyTemplateReqDTO {

    @NotEmpty(message = "模版名称不能为空")
    private String name;

    @NotNull(message = "模版编码不能为空")
    private String code;

    @NotNull(message = "模版类型不能为空")
    private Integer type;

    @NotEmpty(message = "发送人名称不能为空")
    private String nickname;

    @NotEmpty(message = "模版内容不能为空")
    private String content;

    @NotNull(message = "状态不能为空")
    @InEnum(value = CommonStatusEnum.class, message = "状态必须是 {value}")
    private Integer status;

    private String remark;

}
