package com.stonewu.aifusion.module.trade.controller.app.brokerage.vo.user;

import com.stonewu.aifusion.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.stonewu.aifusion.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "应用 App - 分销用户排行 Request VO")
@Data
public class AppBrokerageUserRankPageReqVO extends PageParam {

    @Schema(description = "开始 + 结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @NotEmpty(message = "时间不能为空")
    private LocalDateTime[] times;

}
