package com.stonewu.aifusion.module.pay.service.order;

import com.stonewu.aifusion.module.pay.service.channel.PayAppServiceImpl;
import com.stonewu.aifusion.module.pay.service.channel.PayChannelServiceImpl;
import com.stonewu.aifusion.module.pay.service.order.dto.PayOrderCreateReqDTO;
import com.stonewu.aifusion.module.pay.service.order.dto.PayOrderSubmitReqDTO;
import com.stonewu.aifusion.module.pay.test.BaseDbIntegrationTest;
import com.stonewu.aifusion.framework.common.util.date.DateUtils;
import com.stonewu.aifusion.framework.pay.config.AiFusionPayAutoConfiguration;
import com.stonewu.aifusion.framework.pay.core.enums.PayChannelEnum;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import jakarta.annotation.Resource;
import java.time.Duration;

@Import({PayOrderServiceImpl.class, PayAppServiceImpl.class,
        PayChannelServiceImpl.class, AiFusionPayAutoConfiguration.class})
public class PayOrderServiceIntegrationTest extends BaseDbIntegrationTest {

    @Resource
    private PayOrderService payOrderService;

    @Test
    public void testCreatePayOrder() {
        // 构造请求
        PayOrderCreateReqDTO reqDTO = new PayOrderCreateReqDTO();
        reqDTO.setAppId(6L);
        reqDTO.setUserIp("127.0.0.1");
        reqDTO.setMerchantOrderId(String.valueOf(System.currentTimeMillis()));
        reqDTO.setSubject("标题");
        reqDTO.setBody("内容");
        reqDTO.setPrice(100);
        reqDTO.setExpireTime(DateUtils.addTime(Duration.ofDays(1)));
        // 发起请求
        payOrderService.createPayOrder(reqDTO);
    }

    @Test
    public void testSubmitPayOrder() {
        // 构造请求
        PayOrderSubmitReqDTO reqDTO = new PayOrderSubmitReqDTO();
        reqDTO.setId(10L);
        reqDTO.setAppId(6L);
        reqDTO.setChannelCode(PayChannelEnum.WX_PUB.getCode());
        reqDTO.setUserIp("127.0.0.1");
        // 发起请求
        payOrderService.submitPayOrder(reqDTO);
    }

}
