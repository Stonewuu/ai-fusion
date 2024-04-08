package com.stonewu.aifusion.framework.pay.core.client.impl.alipay;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.RandomUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.DefaultSigner;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.stonewu.aifusion.framework.common.exception.ServiceException;
import com.stonewu.aifusion.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.stonewu.aifusion.framework.common.exception.util.ServiceExceptionUtil;
import com.stonewu.aifusion.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.stonewu.aifusion.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import com.stonewu.aifusion.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import com.stonewu.aifusion.framework.pay.core.client.exception.PayException;
import com.stonewu.aifusion.framework.pay.core.enums.refund.PayRefundStatusRespEnum;
import com.stonewu.aifusion.framework.test.core.ut.BaseMockitoUnitTest;
import com.stonewu.aifusion.framework.test.core.util.RandomUtils;
import jakarta.validation.ConstraintViolationException;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;

import java.util.Date;

import static com.stonewu.aifusion.framework.pay.core.client.impl.alipay.AlipayPayClientConfig.MODE_PUBLIC_KEY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

/**
 * 支付宝 Client 的测试基类
 *
 * @author jason
 */
public abstract class AbstractAlipayClientTest extends BaseMockitoUnitTest {

    protected AlipayPayClientConfig config = RandomUtils.randomPojo(AlipayPayClientConfig.class, o -> {
        o.setServerUrl(RandomUtils.randomURL());
        o.setPrivateKey(RandomUtils.randomString());
        o.setMode(MODE_PUBLIC_KEY);
        o.setSignType(AlipayPayClientConfig.SIGN_TYPE_DEFAULT);
        o.setAppCertContent("");
        o.setAlipayPublicCertContent("");
        o.setRootCertContent("");
    });

    @Mock
    protected DefaultAlipayClient defaultAlipayClient;

    @Setter
    private AbstractAlipayPayClient client;

    /**
     * 子类需要实现该方法. 设置 client 的具体实现
     */
    @BeforeEach
    public abstract void setUp();

    @Test
    @DisplayName("支付宝 Client 初始化")
    public void testDoInit() {
        // 调用
        client.doInit();
        // 断言
        DefaultAlipayClient realClient = client.getClient();
        assertNotSame(defaultAlipayClient, realClient);
        assertInstanceOf(DefaultSigner.class, realClient.getSigner());
        assertEquals(config.getPrivateKey(), ((DefaultSigner) realClient.getSigner()).getPrivateKey());
    }

    @Test
    @DisplayName("支付宝 Client 统一退款：成功")
    public void testUnifiedRefund_success() throws AlipayApiException {
        // mock 方法
        String notifyUrl = RandomUtils.randomURL();
        Date refundTime = RandomUtils.randomDate();
        String outRefundNo = RandomUtils.randomString();
        String outTradeNo = RandomUtils.randomString();
        Integer refundAmount = RandomUtils.randomInteger();
        AlipayTradeRefundResponse response = RandomUtils.randomPojo(AlipayTradeRefundResponse.class, o -> {
            o.setSubCode("");
            o.setGmtRefundPay(refundTime);
        });
        when(defaultAlipayClient.execute(argThat((ArgumentMatcher<AlipayTradeRefundRequest>) request -> {
            assertInstanceOf(AlipayTradeRefundModel.class, request.getBizModel());
            AlipayTradeRefundModel bizModel = (AlipayTradeRefundModel) request.getBizModel();
            assertEquals(outRefundNo, bizModel.getOutRequestNo());
            assertEquals(outTradeNo, bizModel.getOutTradeNo());
            assertEquals(String.valueOf(refundAmount / 100.0), bizModel.getRefundAmount());
            return true;
        }))).thenReturn(response);
        // 准备请求参数
        PayRefundUnifiedReqDTO refundReqDTO = RandomUtils.randomPojo(PayRefundUnifiedReqDTO.class, o -> {
            o.setOutRefundNo(outRefundNo);
            o.setOutTradeNo(outTradeNo);
            o.setNotifyUrl(notifyUrl);
            o.setRefundPrice(refundAmount);
        });

        // 调用
        PayRefundRespDTO resp = client.unifiedRefund(refundReqDTO);
        // 断言
        assertEquals(PayRefundStatusRespEnum.SUCCESS.getStatus(), resp.getStatus());
        assertEquals(outRefundNo, resp.getOutRefundNo());
        assertNull(resp.getChannelRefundNo());
        assertEquals(LocalDateTimeUtil.of(refundTime), resp.getSuccessTime());
        assertSame(response, resp.getRawData());
        assertNull(resp.getChannelErrorCode());
        assertNull(resp.getChannelErrorMsg());
    }

    @Test
    @DisplayName("支付宝 Client 统一退款：渠道返回失败")
    public void test_unified_refund_channel_failed() throws AlipayApiException {
        // mock 方法
        String notifyUrl = RandomUtils.randomURL();
        String subCode = RandomUtils.randomString();
        String subMsg = RandomUtils.randomString();
        AlipayTradeRefundResponse response = RandomUtils.randomPojo(AlipayTradeRefundResponse.class, o -> {
            o.setSubCode(subCode);
            o.setSubMsg(subMsg);
        });
        when(defaultAlipayClient.execute(argThat((ArgumentMatcher<AlipayTradeRefundRequest>) request -> {
            assertInstanceOf(AlipayTradeRefundModel.class, request.getBizModel());
            return true;
        }))).thenReturn(response);
        // 准备请求参数
        String outRefundNo = RandomUtils.randomString();
        String outTradeNo = RandomUtils.randomString();
        PayRefundUnifiedReqDTO refundReqDTO = RandomUtils.randomPojo(PayRefundUnifiedReqDTO.class, o -> {
            o.setOutRefundNo(outRefundNo);
            o.setOutTradeNo(outTradeNo);
            o.setNotifyUrl(notifyUrl);
        });

        // 调用
        PayRefundRespDTO resp = client.unifiedRefund(refundReqDTO);
        // 断言
        assertEquals(PayRefundStatusRespEnum.FAILURE.getStatus(), resp.getStatus());
        assertEquals(outRefundNo, resp.getOutRefundNo());
        assertNull(resp.getChannelRefundNo());
        assertNull(resp.getSuccessTime());
        assertSame(response, resp.getRawData());
        assertEquals(subCode, resp.getChannelErrorCode());
        assertEquals(subMsg, resp.getChannelErrorMsg());
    }

    @Test
    @DisplayName("支付宝 Client 统一退款：参数校验不通过")
    public void testUnifiedRefund_paramInvalidate() {
        // 准备请求参数
        String notifyUrl = RandomUtils.randomURL();
        PayRefundUnifiedReqDTO refundReqDTO = RandomUtils.randomPojo(PayRefundUnifiedReqDTO.class, o -> {
            o.setOutTradeNo("");
            o.setNotifyUrl(notifyUrl);
        });

        // 调用，并断言
        assertThrows(ConstraintViolationException.class, () -> client.unifiedRefund(refundReqDTO));
    }

    @Test
    @DisplayName("支付宝 Client 统一退款：抛出业务异常")
    public void testUnifiedRefund_throwServiceException() throws AlipayApiException {
        // mock 方法
        when(defaultAlipayClient.execute(argThat((ArgumentMatcher<AlipayTradeRefundRequest>) request -> true)))
                .thenThrow(ServiceExceptionUtil.exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR));
        // 准备请求参数
        String notifyUrl = RandomUtils.randomURL();
        PayRefundUnifiedReqDTO refundReqDTO = RandomUtils.randomPojo(PayRefundUnifiedReqDTO.class, o -> o.setNotifyUrl(notifyUrl));

        // 调用，并断言
        assertThrows(ServiceException.class, () -> client.unifiedRefund(refundReqDTO));
    }

    @Test
    @DisplayName("支付宝 Client 统一退款：抛出系统异常")
    public void testUnifiedRefund_throwPayException() throws AlipayApiException {
        // mock 方法
        when(defaultAlipayClient.execute(argThat((ArgumentMatcher<AlipayTradeRefundRequest>) request -> true)))
                .thenThrow(new RuntimeException("系统异常"));
        // 准备请求参数
        String notifyUrl = RandomUtils.randomURL();
        PayRefundUnifiedReqDTO refundReqDTO = RandomUtils.randomPojo(PayRefundUnifiedReqDTO.class, o -> o.setNotifyUrl(notifyUrl));

        // 调用，并断言
        assertThrows(PayException.class, () -> client.unifiedRefund(refundReqDTO));
    }

    @Test
    @DisplayName("支付宝 Client 统一下单：参数校验不通过")
    public void testUnifiedOrder_paramInvalidate() {
        // 准备请求参数
        String outTradeNo = RandomUtils.randomString();
        String notifyUrl = RandomUtils.randomURL();
        PayOrderUnifiedReqDTO reqDTO = RandomUtils.randomPojo(PayOrderUnifiedReqDTO.class, o -> {
            o.setOutTradeNo(outTradeNo);
            o.setNotifyUrl(notifyUrl);
        });

        // 调用，并断言
        assertThrows(ConstraintViolationException.class, () -> client.unifiedOrder(reqDTO));
    }

    protected PayOrderUnifiedReqDTO buildOrderUnifiedReqDTO(String notifyUrl, String outTradeNo, Integer price) {
        return RandomUtils.randomPojo(PayOrderUnifiedReqDTO.class, o -> {
            o.setOutTradeNo(outTradeNo);
            o.setNotifyUrl(notifyUrl);
            o.setPrice(price);
            o.setSubject(RandomUtil.randomString(32));
            o.setBody(RandomUtil.randomString(32));
        });
    }

}
