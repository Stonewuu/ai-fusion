package com.stonewu.aifusion.module.ai.controller.admin.model;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.pojo.CommonResult;
import com.stonewu.aifusion.framework.common.util.object.BeanUtils;
import static com.stonewu.aifusion.framework.common.pojo.CommonResult.success;

import com.stonewu.aifusion.framework.excel.core.util.ExcelUtils;

import com.stonewu.aifusion.framework.apilog.core.annotations.ApiAccessLog;
import static com.stonewu.aifusion.framework.apilog.core.enums.OperateTypeEnum.*;

import com.stonewu.aifusion.module.ai.controller.admin.model.vo.*;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.ModelDO;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.AssistantDO;
import com.stonewu.aifusion.module.ai.service.model.ModelService;

@Tag(name = "管理后台 - AI模型")
@RestController
@RequestMapping("/ai/model")
@Validated
public class ModelController {

    @Resource
    private ModelService modelService;

    @PostMapping("/create")
    @Operation(summary = "创建AI模型")
    @PreAuthorize("@ss.hasPermission('ai:model:create')")
    public CommonResult<Long> createModel(@Valid @RequestBody ModelSaveReqVO createReqVO) {
        return success(modelService.createModel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新AI模型")
    @PreAuthorize("@ss.hasPermission('ai:model:update')")
    public CommonResult<Boolean> updateModel(@Valid @RequestBody ModelSaveReqVO updateReqVO) {
        modelService.updateModel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除AI模型")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ai:model:delete')")
    public CommonResult<Boolean> deleteModel(@RequestParam("id") Long id) {
        modelService.deleteModel(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得AI模型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('ai:model:query')")
    public CommonResult<ModelRespVO> getModel(@RequestParam("id") Long id) {
        ModelDO model = modelService.getModel(id);
        return success(BeanUtils.toBean(model, ModelRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得AI模型分页")
    @PreAuthorize("@ss.hasPermission('ai:model:query')")
    public CommonResult<PageResult<ModelRespVO>> getModelPage(@Valid ModelPageReqVO pageReqVO) {
        PageResult<ModelDO> pageResult = modelService.getModelPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ModelRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出AI模型 Excel")
    @PreAuthorize("@ss.hasPermission('ai:model:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportModelExcel(@Valid ModelPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ModelDO> list = modelService.getModelPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "AI模型.xls", "数据", ModelRespVO.class,
                        BeanUtils.toBean(list, ModelRespVO.class));
    }

    // ==================== 子表（AI助手） ====================

    @GetMapping("/assistant/page")
    @Operation(summary = "获得AI助手分页")
    @Parameter(name = "modelId", description = "AI模型编号")
    @PreAuthorize("@ss.hasPermission('ai:model:query')")
    public CommonResult<PageResult<AssistantDO>> getAssistantPage(PageParam pageReqVO,
                                                                                        @RequestParam("modelId") Long modelId) {
        return success(modelService.getAssistantPage(pageReqVO, modelId));
    }

    @PostMapping("/assistant/create")
    @Operation(summary = "创建AI助手")
    @PreAuthorize("@ss.hasPermission('ai:model:create')")
    public CommonResult<Long> createAssistant(@Valid @RequestBody AssistantDO assistant) {
        return success(modelService.createAssistant(assistant));
    }

    @PutMapping("/assistant/update")
    @Operation(summary = "更新AI助手")
    @PreAuthorize("@ss.hasPermission('ai:model:update')")
    public CommonResult<Boolean> updateAssistant(@Valid @RequestBody AssistantDO assistant) {
        modelService.updateAssistant(assistant);
        return success(true);
    }

    @DeleteMapping("/assistant/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除AI助手")
    @PreAuthorize("@ss.hasPermission('ai:model:delete')")
    public CommonResult<Boolean> deleteAssistant(@RequestParam("id") Long id) {
        modelService.deleteAssistant(id);
        return success(true);
    }

	@GetMapping("/assistant/get")
	@Operation(summary = "获得AI助手")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ai:model:query')")
	public CommonResult<AssistantDO> getAssistant(@RequestParam("id") Long id) {
	    return success(modelService.getAssistant(id));
	}

}