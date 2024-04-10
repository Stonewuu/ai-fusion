package com.stonewu.aifusion.module.trade.service.aftersale;

import com.stonewu.aifusion.module.trade.convert.aftersale.AfterSaleLogConvert;
import com.stonewu.aifusion.module.trade.dal.dataobject.aftersale.AfterSaleLogDO;
import com.stonewu.aifusion.module.trade.dal.mysql.aftersale.AfterSaleLogMapper;
import com.stonewu.aifusion.module.trade.service.aftersale.bo.AfterSaleLogCreateReqBO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 交易售后日志 Service 实现类
 *
 *
 */
@Service
@Validated
public class AfterSaleLogServiceImpl implements AfterSaleLogService {

    @Resource
    private AfterSaleLogMapper afterSaleLogMapper;

    @Override
    public void createAfterSaleLog(AfterSaleLogCreateReqBO createReqBO) {
        AfterSaleLogDO afterSaleLog = AfterSaleLogConvert.INSTANCE.convert(createReqBO);
        afterSaleLogMapper.insert(afterSaleLog);
    }

    @Override
    public List<AfterSaleLogDO> getAfterSaleLogList(Long afterSaleId) {
        return afterSaleLogMapper.selectListByAfterSaleId(afterSaleId);
    }

}
