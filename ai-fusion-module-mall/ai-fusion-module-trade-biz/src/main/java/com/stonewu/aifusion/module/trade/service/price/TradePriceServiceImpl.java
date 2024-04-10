package com.stonewu.aifusion.module.trade.service.price;

import com.stonewu.aifusion.framework.common.exception.util.ServiceExceptionUtil;
import com.stonewu.aifusion.module.product.api.sku.ProductSkuApi;
import com.stonewu.aifusion.module.product.api.sku.dto.ProductSkuRespDTO;
import com.stonewu.aifusion.module.product.api.spu.ProductSpuApi;
import com.stonewu.aifusion.module.product.api.spu.dto.ProductSpuRespDTO;
import com.stonewu.aifusion.module.product.enums.ErrorCodeConstants;
import com.stonewu.aifusion.module.trade.service.price.bo.TradePriceCalculateReqBO;
import com.stonewu.aifusion.module.trade.service.price.bo.TradePriceCalculateRespBO;
import com.stonewu.aifusion.module.trade.service.price.calculator.TradePriceCalculator;
import com.stonewu.aifusion.module.trade.service.price.calculator.TradePriceCalculatorHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

import static com.stonewu.aifusion.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.stonewu.aifusion.framework.common.util.collection.CollectionUtils.convertMap;
import static com.stonewu.aifusion.framework.common.util.collection.CollectionUtils.convertSet;
import static com.stonewu.aifusion.module.trade.enums.ErrorCodeConstants.PRICE_CALCULATE_PAY_PRICE_ILLEGAL;

/**
 * 价格计算 Service 实现类
 *
 *
 */
@Service
@Validated
@Slf4j
public class TradePriceServiceImpl implements TradePriceService {

    @Resource
    private ProductSkuApi productSkuApi;
    @Resource
    private ProductSpuApi productSpuApi;

    @Resource
    private List<TradePriceCalculator> priceCalculators;

    @Override
    public TradePriceCalculateRespBO calculatePrice(TradePriceCalculateReqBO calculateReqBO) {
        // 1.1 获得商品 SKU 数组
        List<ProductSkuRespDTO> skuList = checkSkuList(calculateReqBO);
        // 1.2 获得商品 SPU 数组
        List<ProductSpuRespDTO> spuList = checkSpuList(skuList);

        // 2.1 计算价格
        TradePriceCalculateRespBO calculateRespBO = TradePriceCalculatorHelper
                .buildCalculateResp(calculateReqBO, spuList, skuList);
        priceCalculators.forEach(calculator -> calculator.calculate(calculateReqBO, calculateRespBO));
        // 2.2  如果最终支付金额小于等于 0，则抛出业务异常
        if (calculateRespBO.getPrice().getPayPrice() <= 0) {
            log.error("[calculatePrice][价格计算不正确，请求 calculateReqDTO({})，结果 priceCalculate({})]",
                    calculateReqBO, calculateRespBO);
            throw exception(PRICE_CALCULATE_PAY_PRICE_ILLEGAL);
        }
        return calculateRespBO;
    }

    private List<ProductSkuRespDTO> checkSkuList(TradePriceCalculateReqBO reqBO) {
        // 获得商品 SKU 数组
        Map<Long, Integer> skuIdCountMap = convertMap(reqBO.getItems(),
                TradePriceCalculateReqBO.Item::getSkuId, TradePriceCalculateReqBO.Item::getCount);
        List<ProductSkuRespDTO> skus = productSkuApi.getSkuList(skuIdCountMap.keySet());

        // 校验商品 SKU
        skus.forEach(sku -> {
            Integer count = skuIdCountMap.get(sku.getId());
            if (count == null) {
                throw ServiceExceptionUtil.exception(ErrorCodeConstants.SKU_NOT_EXISTS);
            }
            if (count > sku.getStock()) {
                throw ServiceExceptionUtil.exception(ErrorCodeConstants.SKU_STOCK_NOT_ENOUGH);
            }
        });
        return skus;
    }

    private List<ProductSpuRespDTO> checkSpuList(List<ProductSkuRespDTO> skuList) {
        // 获得商品 SPU 数组
        return productSpuApi.validateSpuList(convertSet(skuList, ProductSkuRespDTO::getSpuId));
    }

}
