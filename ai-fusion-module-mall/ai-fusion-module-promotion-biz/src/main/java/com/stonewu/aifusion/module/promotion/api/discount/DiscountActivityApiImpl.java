package com.stonewu.aifusion.module.promotion.api.discount;

import com.stonewu.aifusion.module.promotion.api.discount.dto.DiscountProductRespDTO;
import com.stonewu.aifusion.module.promotion.convert.discount.DiscountActivityConvert;
import com.stonewu.aifusion.module.promotion.service.discount.DiscountActivityService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

/**
 * 限时折扣 API 实现类
 *
 *
 */
@Service
@Validated
public class DiscountActivityApiImpl implements DiscountActivityApi {

    @Resource
    private DiscountActivityService discountActivityService;

    @Override
    public List<DiscountProductRespDTO> getMatchDiscountProductList(Collection<Long> skuIds) {
        return DiscountActivityConvert.INSTANCE.convertList02(discountActivityService.getMatchDiscountProductList(skuIds));
    }

}
