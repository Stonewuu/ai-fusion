package com.stonewu.aifusion.module.product.controller.admin.spu.vo;

import com.stonewu.aifusion.framework.common.validation.InEnum;
import com.stonewu.aifusion.module.product.enums.spu.ProductSpuStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 商品 SPU Status 更新 Request VO")
@Data
public class ProductSpuUpdateStatusReqVO{

    @Schema(description = "商品编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "商品编号不能为空")
    private Long id;

    @Schema(description = "商品状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "商品状态不能为空")
    @InEnum(ProductSpuStatusEnum.class)
    private Integer status;

}
