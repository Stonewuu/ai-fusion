package com.stonewu.aifusion.module.promotion.controller.app.coupon.vo.coupon;

import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.common.validation.InEnum;
import com.stonewu.aifusion.module.promotion.enums.coupon.CouponStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "用户 App - 优惠劵分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppCouponPageReqVO extends PageParam {

    @Schema(description = "优惠劵状态", example = "1")
    @InEnum(value = CouponStatusEnum.class, message = "优惠劵状态，必须是 {value}")
    private Integer status;

}
