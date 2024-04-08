package com.stonewu.aifusion.module.trade.api.order;

import com.stonewu.aifusion.module.trade.api.order.dto.TradeOrderRespDTO;
import com.stonewu.aifusion.module.trade.convert.order.TradeOrderConvert;
import com.stonewu.aifusion.module.trade.service.order.TradeOrderQueryService;
import com.stonewu.aifusion.module.trade.service.order.TradeOrderUpdateService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

/**
 * 订单 API 接口实现类
 *
 * @author HUIHUI
 */
@Service
@Validated
public class TradeOrderApiImpl implements TradeOrderApi {

    @Resource
    private TradeOrderUpdateService tradeOrderUpdateService;
    @Resource
    private TradeOrderQueryService tradeOrderQueryService;

    @Override
    public List<TradeOrderRespDTO> getOrderList(Collection<Long> ids) {
        return TradeOrderConvert.INSTANCE.convertList04(tradeOrderQueryService.getOrderList(ids));
    }

    @Override
    public TradeOrderRespDTO getOrder(Long id) {
        return TradeOrderConvert.INSTANCE.convert(tradeOrderQueryService.getOrder(id));
    }

    @Override
    public void cancelPaidOrder(Long userId, Long orderId) {
        tradeOrderUpdateService.cancelPaidOrder(userId, orderId);
    }

}
