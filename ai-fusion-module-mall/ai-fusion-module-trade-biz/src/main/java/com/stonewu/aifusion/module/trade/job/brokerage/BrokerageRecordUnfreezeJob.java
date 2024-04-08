package com.stonewu.aifusion.module.trade.job.brokerage;

import cn.hutool.core.util.StrUtil;
import com.stonewu.aifusion.framework.quartz.core.handler.JobHandler;
import com.stonewu.aifusion.framework.tenant.core.job.TenantJob;
import com.stonewu.aifusion.module.trade.service.brokerage.BrokerageRecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 佣金解冻 Job
 *
 * @author owen
 */
@Component
public class BrokerageRecordUnfreezeJob implements JobHandler {

    @Resource
    private BrokerageRecordService brokerageRecordService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = brokerageRecordService.unfreezeRecord();
        return StrUtil.format("解冻佣金 {} 个", count);
    }

}
