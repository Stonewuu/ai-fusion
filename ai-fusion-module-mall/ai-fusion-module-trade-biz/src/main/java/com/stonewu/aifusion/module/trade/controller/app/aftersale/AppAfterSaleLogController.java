package com.stonewu.aifusion.module.trade.controller.app.aftersale;

import com.stonewu.aifusion.framework.common.pojo.CommonResult;
import com.stonewu.aifusion.framework.common.util.object.BeanUtils;
import com.stonewu.aifusion.module.trade.controller.app.aftersale.vo.log.AppAfterSaleLogRespVO;
import com.stonewu.aifusion.module.trade.dal.dataobject.aftersale.AfterSaleLogDO;
import com.stonewu.aifusion.module.trade.service.aftersale.AfterSaleLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.stonewu.aifusion.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 售后日志")
@RestController
@RequestMapping("/trade/after-sale-log")
@Validated
@Slf4j
public class AppAfterSaleLogController {

    @Resource
    private AfterSaleLogService afterSaleLogService;

    @GetMapping("/list")
    @Operation(summary = "获得售后日志列表")
    @Parameter(name = "afterSaleId", description = "售后编号", required = true, example = "1")
    public CommonResult<List<AppAfterSaleLogRespVO>> getAfterSaleLogList(
            @RequestParam("afterSaleId") Long afterSaleId) {
        List<AfterSaleLogDO> logs = afterSaleLogService.getAfterSaleLogList(afterSaleId);
        return success(BeanUtils.toBean(logs, AppAfterSaleLogRespVO.class));
    }

}
