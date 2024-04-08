package com.stonewu.aifusion.module.trade.service.order;

import com.stonewu.aifusion.module.trade.convert.order.TradeOrderLogConvert;
import com.stonewu.aifusion.module.trade.dal.dataobject.order.TradeOrderLogDO;
import com.stonewu.aifusion.module.trade.dal.mysql.order.TradeOrderLogMapper;
import com.stonewu.aifusion.module.trade.service.order.bo.TradeOrderLogCreateReqBO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 交易下单日志 Service 实现类
 *
 * @author 陈賝
 * @since 2023/7/6 15:44
 */
@Service
public class TradeOrderLogServiceImpl implements TradeOrderLogService {

    @Resource
    private TradeOrderLogMapper tradeOrderLogMapper;

    @Override
    public void createOrderLog(TradeOrderLogCreateReqBO createReqBO) {
        tradeOrderLogMapper.insert(TradeOrderLogConvert.INSTANCE.convert(createReqBO));
    }

    @Override
    public List<TradeOrderLogDO> getOrderLogListByOrderId(Long orderId) {
        return tradeOrderLogMapper.selectListByOrderId(orderId);
    }

}
