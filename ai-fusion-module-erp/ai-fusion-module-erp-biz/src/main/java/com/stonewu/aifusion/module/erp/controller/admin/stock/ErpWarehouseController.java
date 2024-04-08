package com.stonewu.aifusion.module.erp.controller.admin.stock;

import com.stonewu.aifusion.framework.apilog.core.annotations.ApiAccessLog;
import com.stonewu.aifusion.framework.common.enums.CommonStatusEnum;
import com.stonewu.aifusion.framework.common.pojo.CommonResult;
import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.util.object.BeanUtils;
import com.stonewu.aifusion.framework.excel.core.util.ExcelUtils;
import com.stonewu.aifusion.module.erp.controller.admin.stock.vo.warehouse.ErpWarehousePageReqVO;
import com.stonewu.aifusion.module.erp.controller.admin.stock.vo.warehouse.ErpWarehouseRespVO;
import com.stonewu.aifusion.module.erp.controller.admin.stock.vo.warehouse.ErpWarehouseSaveReqVO;
import com.stonewu.aifusion.module.erp.dal.dataobject.stock.ErpWarehouseDO;
import com.stonewu.aifusion.module.erp.service.stock.ErpWarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.stonewu.aifusion.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.stonewu.aifusion.framework.common.pojo.CommonResult.success;
import static com.stonewu.aifusion.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "管理后台 - ERP 仓库")
@RestController
@RequestMapping("/erp/warehouse")
@Validated
public class ErpWarehouseController {

    @Resource
    private ErpWarehouseService warehouseService;

    @PostMapping("/create")
    @Operation(summary = "创建仓库")
    @PreAuthorize("@ss.hasPermission('erp:warehouse:create')")
    public CommonResult<Long> createWarehouse(@Valid @RequestBody ErpWarehouseSaveReqVO createReqVO) {
        return success(warehouseService.createWarehouse(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新仓库")
    @PreAuthorize("@ss.hasPermission('erp:warehouse:update')")
    public CommonResult<Boolean> updateWarehouse(@Valid @RequestBody ErpWarehouseSaveReqVO updateReqVO) {
        warehouseService.updateWarehouse(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-default-status")
    @Operation(summary = "更新仓库默认状态")
    @Parameters({
            @Parameter(name = "id", description = "编号", required = true),
            @Parameter(name = "status", description = "状态", required = true)
    })
    public CommonResult<Boolean> updateWarehouseDefaultStatus(@RequestParam("id") Long id,
                                                              @RequestParam("defaultStatus") Boolean defaultStatus) {
        warehouseService.updateWarehouseDefaultStatus(id, defaultStatus);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除仓库")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('erp:warehouse:delete')")
    public CommonResult<Boolean> deleteWarehouse(@RequestParam("id") Long id) {
        warehouseService.deleteWarehouse(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得仓库")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('erp:warehouse:query')")
    public CommonResult<ErpWarehouseRespVO> getWarehouse(@RequestParam("id") Long id) {
        ErpWarehouseDO warehouse = warehouseService.getWarehouse(id);
        return success(BeanUtils.toBean(warehouse, ErpWarehouseRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得仓库分页")
    @PreAuthorize("@ss.hasPermission('erp:warehouse:query')")
    public CommonResult<PageResult<ErpWarehouseRespVO>> getWarehousePage(@Valid ErpWarehousePageReqVO pageReqVO) {
        PageResult<ErpWarehouseDO> pageResult = warehouseService.getWarehousePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ErpWarehouseRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得仓库精简列表", description = "只包含被开启的仓库，主要用于前端的下拉选项")
    public CommonResult<List<ErpWarehouseRespVO>> getWarehouseSimpleList() {
        List<ErpWarehouseDO> list = warehouseService.getWarehouseListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(convertList(list, warehouse -> new ErpWarehouseRespVO().setId(warehouse.getId())
                .setName(warehouse.getName()).setDefaultStatus(warehouse.getDefaultStatus())));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出仓库 Excel")
    @PreAuthorize("@ss.hasPermission('erp:warehouse:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportWarehouseExcel(@Valid ErpWarehousePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ErpWarehouseDO> list = warehouseService.getWarehousePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "仓库.xls", "数据", ErpWarehouseRespVO.class,
                        BeanUtils.toBean(list, ErpWarehouseRespVO.class));
    }

}