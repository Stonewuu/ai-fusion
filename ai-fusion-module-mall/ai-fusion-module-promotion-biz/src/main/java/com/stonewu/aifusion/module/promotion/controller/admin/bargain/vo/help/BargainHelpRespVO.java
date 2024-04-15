package com.stonewu.aifusion.module.promotion.controller.admin.bargain.vo.help;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 砍价助力 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BargainHelpRespVO extends BargainHelpBaseVO {

    @Schema(description = "砍价助力编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25860")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    // ========== 用户相关 ==========

    @Schema(description = "用户昵称", example = "")
    private String nickname;

    @Schema(description = "用户头像", requiredMode = Schema.RequiredMode.REQUIRED, example = "")
    private String avatar;

}
