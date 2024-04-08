package com.stonewu.aifusion.module.promotion.controller.admin.combination.vo.activity;

import com.stonewu.aifusion.module.promotion.controller.admin.combination.vo.product.CombinationProductBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 拼团活动创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CombinationActivityCreateReqVO extends CombinationActivityBaseVO {

    @Schema(description = "拼团商品", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    private List<CombinationProductBaseVO> products;

}
