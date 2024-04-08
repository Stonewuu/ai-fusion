package com.stonewu.aifusion.module.pay.api.refund;

import com.stonewu.aifusion.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import com.stonewu.aifusion.module.pay.api.refund.dto.PayRefundRespDTO;
import com.stonewu.aifusion.module.pay.convert.refund.PayRefundConvert;
import com.stonewu.aifusion.module.pay.service.refund.PayRefundService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 退款单 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PayRefundApiImpl implements PayRefundApi {

    @Resource
    private PayRefundService payRefundService;

    @Override
    public Long createRefund(PayRefundCreateReqDTO reqDTO) {
        return payRefundService.createPayRefund(reqDTO);
    }

    @Override
    public PayRefundRespDTO getRefund(Long id) {
        return PayRefundConvert.INSTANCE.convert02(payRefundService.getRefund(id));
    }

}
