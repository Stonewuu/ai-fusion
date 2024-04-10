package com.stonewu.aifusion.module.promotion.api.coupon;


import com.stonewu.aifusion.module.promotion.api.coupon.dto.CouponRespDTO;
import com.stonewu.aifusion.module.promotion.api.coupon.dto.CouponUseReqDTO;
import com.stonewu.aifusion.module.promotion.api.coupon.dto.CouponValidReqDTO;
import com.stonewu.aifusion.module.promotion.convert.coupon.CouponConvert;
import com.stonewu.aifusion.module.promotion.dal.dataobject.coupon.CouponDO;
import com.stonewu.aifusion.module.promotion.service.coupon.CouponService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 优惠劵 API 实现类
 *
 *
 */
@Service
@Validated
public class CouponApiImpl implements CouponApi {

    @Resource
    private CouponService couponService;

    @Override
    public void useCoupon(CouponUseReqDTO useReqDTO) {
        couponService.useCoupon(useReqDTO.getId(), useReqDTO.getUserId(),
                useReqDTO.getOrderId());
    }

    @Override
    public void returnUsedCoupon(Long id) {
        couponService.returnUsedCoupon(id);
    }

    @Override
    public CouponRespDTO validateCoupon(CouponValidReqDTO validReqDTO) {
        CouponDO coupon = couponService.validCoupon(validReqDTO.getId(), validReqDTO.getUserId());
        return CouponConvert.INSTANCE.convert(coupon);
    }

}
