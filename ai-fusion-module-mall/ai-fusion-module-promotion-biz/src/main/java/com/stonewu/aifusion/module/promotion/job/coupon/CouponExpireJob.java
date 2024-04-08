package com.stonewu.aifusion.module.promotion.job.coupon;

import cn.hutool.core.util.StrUtil;
import com.stonewu.aifusion.framework.quartz.core.handler.JobHandler;
import com.stonewu.aifusion.framework.tenant.core.job.TenantJob;
import com.stonewu.aifusion.module.promotion.service.coupon.CouponService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

// TODO 芋艿：配置一个 Job
/**
 * 优惠券过期 Job
 *
 * @author owen
 */
@Component
public class CouponExpireJob implements JobHandler {

    @Resource
    private CouponService couponService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = couponService.expireCoupon();
        return StrUtil.format("过期优惠券 {} 个", count);
    }

}
