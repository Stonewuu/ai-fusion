package com.stonewu.aifusion.module.trade.controller.app.brokerage.vo.user;

import com.stonewu.aifusion.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "应用 App - 绑定推广员 Request VO")
@Data
public class AppBrokerageUserBindReqVO extends PageParam {

    @Schema(description = "推广员编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "推广员编号不能为空")
    private Long bindUserId;

}
