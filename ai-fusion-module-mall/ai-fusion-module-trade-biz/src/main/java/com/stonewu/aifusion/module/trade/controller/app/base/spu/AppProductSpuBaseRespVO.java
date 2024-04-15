package com.stonewu.aifusion.module.trade.controller.app.base.spu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商品 SPU 基础 Response VO
 *
 *
 */
@Data
public class AppProductSpuBaseRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "商品 SPU 名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "AI-Fusion")
    private String name;

    @Schema(description = "商品主图地址", example = "https://www.stonewu.com/xx.png")
    private String picUrl;

}
