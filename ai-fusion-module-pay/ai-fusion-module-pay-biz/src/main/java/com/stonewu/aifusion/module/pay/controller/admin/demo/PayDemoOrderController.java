package com.stonewu.aifusion.module.pay.controller.admin.demo;

import com.stonewu.aifusion.framework.common.pojo.CommonResult;
import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.module.pay.api.notify.dto.PayOrderNotifyReqDTO;
import com.stonewu.aifusion.module.pay.api.notify.dto.PayRefundNotifyReqDTO;
import com.stonewu.aifusion.module.pay.controller.admin.demo.vo.order.PayDemoOrderCreateReqVO;
import com.stonewu.aifusion.module.pay.controller.admin.demo.vo.order.PayDemoOrderRespVO;
import com.stonewu.aifusion.module.pay.convert.demo.PayDemoOrderConvert;
import com.stonewu.aifusion.module.pay.dal.dataobject.demo.PayDemoOrderDO;
import com.stonewu.aifusion.module.pay.service.demo.PayDemoOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.stonewu.aifusion.framework.common.pojo.CommonResult.success;
import static com.stonewu.aifusion.framework.common.util.servlet.ServletUtils.getClientIP;
import static com.stonewu.aifusion.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 示例订单")
@RestController
@RequestMapping("/pay/demo-order")
@Validated
public class PayDemoOrderController {

    @Resource
    private PayDemoOrderService payDemoOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建示例订单")
    public CommonResult<Long> createDemoOrder(@Valid @RequestBody PayDemoOrderCreateReqVO createReqVO) {
        return success(payDemoOrderService.createDemoOrder(getLoginUserId(), createReqVO));
    }

    @GetMapping("/page")
    @Operation(summary = "获得示例订单分页")
    public CommonResult<PageResult<PayDemoOrderRespVO>> getDemoOrderPage(@Valid PageParam pageVO) {
        PageResult<PayDemoOrderDO> pageResult = payDemoOrderService.getDemoOrderPage(pageVO);
        return success(PayDemoOrderConvert.INSTANCE.convertPage(pageResult));
    }

    @PostMapping("/update-paid")
    @Operation(summary = "更新示例订单为已支付") // 由 pay-module 支付服务，进行回调，可见 PayNotifyJob
    @PermitAll // 无需登录，安全由 PayDemoOrderService 内部校验实现
    public CommonResult<Boolean> updateDemoOrderPaid(@RequestBody PayOrderNotifyReqDTO notifyReqDTO) {
        payDemoOrderService.updateDemoOrderPaid(Long.valueOf(notifyReqDTO.getMerchantOrderId()),
                notifyReqDTO.getPayOrderId());
        return success(true);
    }

    @PutMapping("/refund")
    @Operation(summary = "发起示例订单的退款")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<Boolean> refundDemoOrder(@RequestParam("id") Long id) {
        payDemoOrderService.refundDemoOrder(id, getClientIP());
        return success(true);
    }

    @PostMapping("/update-refunded")
    @Operation(summary = "更新示例订单为已退款") // 由 pay-module 支付服务，进行回调，可见 PayNotifyJob
    @PermitAll // 无需登录，安全由 PayDemoOrderService 内部校验实现
    public CommonResult<Boolean> updateDemoOrderRefunded(@RequestBody PayRefundNotifyReqDTO notifyReqDTO) {
        payDemoOrderService.updateDemoOrderRefunded(Long.valueOf(notifyReqDTO.getMerchantOrderId()),
                notifyReqDTO.getPayRefundId());
        return success(true);
    }

}
