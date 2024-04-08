package com.stonewu.aifusion.module.pay.job.order;

import cn.hutool.core.util.StrUtil;
import com.stonewu.aifusion.framework.quartz.core.handler.JobHandler;
import com.stonewu.aifusion.framework.tenant.core.job.TenantJob;
import com.stonewu.aifusion.module.pay.service.order.PayOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 支付订单的同步 Job
 *
 * 由于支付订单的状态，是由支付渠道异步通知进行同步，考虑到异步通知可能会失败（小概率），所以需要定时进行同步。
 *
 * @author 芋道源码
 */
@Component
public class PayOrderSyncJob implements JobHandler {

    /**
     * 同步创建时间在 N 分钟之前的订单
     *
     * 为什么同步 10 分钟之前的订单？
     *  因为一个订单发起支付，到支付成功，大多数在 10 分钟内，需要保证轮询到。
     *  如果设置为 30、60 或者更大时间范围，会导致轮询的订单太多，影响性能。当然，你也可以根据自己的业务情况来处理。
     */
    private static final Duration CREATE_TIME_DURATION_BEFORE = Duration.ofMinutes(10);

    @Resource
    private PayOrderService orderService;

    @Override
    @TenantJob
    public String execute(String param) {
        LocalDateTime minCreateTime = LocalDateTime.now().minus(CREATE_TIME_DURATION_BEFORE);
        int count = orderService.syncOrder(minCreateTime);
        return StrUtil.format("同步支付订单 {} 个", count);
    }

}
