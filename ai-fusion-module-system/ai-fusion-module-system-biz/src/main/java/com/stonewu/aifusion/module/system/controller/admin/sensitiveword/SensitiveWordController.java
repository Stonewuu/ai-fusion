package com.stonewu.aifusion.module.system.controller.admin.sensitiveword;

import com.stonewu.aifusion.framework.apilog.core.annotations.ApiAccessLog;
import com.stonewu.aifusion.framework.common.pojo.CommonResult;
import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.util.object.BeanUtils;
import com.stonewu.aifusion.framework.excel.core.util.ExcelUtils;
import com.stonewu.aifusion.module.system.controller.admin.sensitiveword.vo.SensitiveWordPageReqVO;
import com.stonewu.aifusion.module.system.controller.admin.sensitiveword.vo.SensitiveWordRespVO;
import com.stonewu.aifusion.module.system.controller.admin.sensitiveword.vo.SensitiveWordSaveVO;
import com.stonewu.aifusion.module.system.dal.dataobject.sensitiveword.SensitiveWordDO;
import com.stonewu.aifusion.module.system.service.sensitiveword.SensitiveWordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.stonewu.aifusion.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.stonewu.aifusion.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 敏感词")
@RestController
@RequestMapping("/system/sensitive-word")
@Validated
public class SensitiveWordController {

    @Resource
    private SensitiveWordService sensitiveWordService;

    @PostMapping("/create")
    @Operation(summary = "创建敏感词")
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:create')")
    public CommonResult<Long> createSensitiveWord(@Valid @RequestBody SensitiveWordSaveVO createReqVO) {
        return success(sensitiveWordService.createSensitiveWord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新敏感词")
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:update')")
    public CommonResult<Boolean> updateSensitiveWord(@Valid @RequestBody SensitiveWordSaveVO updateReqVO) {
        sensitiveWordService.updateSensitiveWord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除敏感词")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:delete')")
    public CommonResult<Boolean> deleteSensitiveWord(@RequestParam("id") Long id) {
        sensitiveWordService.deleteSensitiveWord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得敏感词")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:query')")
    public CommonResult<SensitiveWordRespVO> getSensitiveWord(@RequestParam("id") Long id) {
        SensitiveWordDO sensitiveWord = sensitiveWordService.getSensitiveWord(id);
        return success(BeanUtils.toBean(sensitiveWord, SensitiveWordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得敏感词分页")
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:query')")
    public CommonResult<PageResult<SensitiveWordRespVO>> getSensitiveWordPage(@Valid SensitiveWordPageReqVO pageVO) {
        PageResult<SensitiveWordDO> pageResult = sensitiveWordService.getSensitiveWordPage(pageVO);
        return success(BeanUtils.toBean(pageResult, SensitiveWordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出敏感词 Excel")
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSensitiveWordExcel(@Valid SensitiveWordPageReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SensitiveWordDO> list = sensitiveWordService.getSensitiveWordPage(exportReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "敏感词.xls", "数据", SensitiveWordRespVO.class,
                BeanUtils.toBean(list, SensitiveWordRespVO.class));
    }

    @GetMapping("/get-tags")
    @Operation(summary = "获取所有敏感词的标签数组")
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:query')")
    public CommonResult<Set<String>> getSensitiveWordTagSet() {
        return success(sensitiveWordService.getSensitiveWordTagSet());
    }

    @GetMapping("/validate-text")
    @Operation(summary = "获得文本所包含的不合法的敏感词数组")
    public CommonResult<List<String>> validateText(@RequestParam("text") String text,
                                                   @RequestParam(value = "tags", required = false) List<String> tags) {
        return success(sensitiveWordService.validateText(text, tags));
    }

}
