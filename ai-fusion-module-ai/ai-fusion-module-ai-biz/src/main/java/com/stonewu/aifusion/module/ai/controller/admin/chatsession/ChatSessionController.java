package com.stonewu.aifusion.module.ai.controller.admin.chatsession;

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

import com.stonewu.aifusion.module.ai.controller.admin.chatsession.vo.*;
import com.stonewu.aifusion.module.ai.dal.dataobject.chatsession.ChatSessionDO;
import com.stonewu.aifusion.module.ai.dal.dataobject.chatsession.ChatRecordDO;
import com.stonewu.aifusion.module.ai.service.chatsession.ChatSessionService;

@Tag(name = "管理后台 - 对话记录")
@RestController
@RequestMapping("/ai/chat-session")
@Validated
public class ChatSessionController {

    @Resource
    private ChatSessionService chatSessionService;

    @PostMapping("/create")
    @Operation(summary = "创建对话记录")
    @PreAuthorize("@ss.hasPermission('ai:chat-session:create')")
    public CommonResult<Long> createChatSession(@Valid @RequestBody ChatSessionSaveReqVO createReqVO) {
        return success(chatSessionService.createChatSession(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新对话记录")
    @PreAuthorize("@ss.hasPermission('ai:chat-session:update')")
    public CommonResult<Boolean> updateChatSession(@Valid @RequestBody ChatSessionSaveReqVO updateReqVO) {
        chatSessionService.updateChatSession(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除对话记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ai:chat-session:delete')")
    public CommonResult<Boolean> deleteChatSession(@RequestParam("id") Long id) {
        chatSessionService.deleteChatSession(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得对话记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('ai:chat-session:query')")
    public CommonResult<ChatSessionRespVO> getChatSession(@RequestParam("id") Long id) {
        ChatSessionDO chatSession = chatSessionService.getChatSession(id);
        return success(BeanUtils.toBean(chatSession, ChatSessionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得对话记录分页")
    @PreAuthorize("@ss.hasPermission('ai:chat-session:query')")
    public CommonResult<PageResult<ChatSessionRespVO>> getChatSessionPage(@Valid ChatSessionPageReqVO pageReqVO) {
        PageResult<ChatSessionDO> pageResult = chatSessionService.getChatSessionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ChatSessionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出对话记录 Excel")
    @PreAuthorize("@ss.hasPermission('ai:chat-session:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportChatSessionExcel(@Valid ChatSessionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ChatSessionDO> list = chatSessionService.getChatSessionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "对话记录.xls", "数据", ChatSessionRespVO.class,
                        BeanUtils.toBean(list, ChatSessionRespVO.class));
    }

    // ==================== 子表（对话记录） ====================

    @GetMapping("/chat-record/page")
    @Operation(summary = "获得对话记录分页")
    @Parameter(name = "sessionId", description = "会话ID")
    @PreAuthorize("@ss.hasPermission('ai:chat-session:query')")
    public CommonResult<PageResult<ChatRecordDO>> getChatRecordPage(PageParam pageReqVO,
                                                                                        @RequestParam("sessionId") Long sessionId) {
        return success(chatSessionService.getChatRecordPage(pageReqVO, sessionId));
    }

    @PostMapping("/chat-record/create")
    @Operation(summary = "创建对话记录")
    @PreAuthorize("@ss.hasPermission('ai:chat-session:create')")
    public CommonResult<Long> createChatRecord(@Valid @RequestBody ChatRecordDO chatRecord) {
        return success(chatSessionService.createChatRecord(chatRecord));
    }

    @PutMapping("/chat-record/update")
    @Operation(summary = "更新对话记录")
    @PreAuthorize("@ss.hasPermission('ai:chat-session:update')")
    public CommonResult<Boolean> updateChatRecord(@Valid @RequestBody ChatRecordDO chatRecord) {
        chatSessionService.updateChatRecord(chatRecord);
        return success(true);
    }

    @DeleteMapping("/chat-record/delete")
    @Parameter(name = "id", description = "编号", required = true)
    @Operation(summary = "删除对话记录")
    @PreAuthorize("@ss.hasPermission('ai:chat-session:delete')")
    public CommonResult<Boolean> deleteChatRecord(@RequestParam("id") Long id) {
        chatSessionService.deleteChatRecord(id);
        return success(true);
    }

	@GetMapping("/chat-record/get")
	@Operation(summary = "获得对话记录")
	@Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ai:chat-session:query')")
	public CommonResult<ChatRecordDO> getChatRecord(@RequestParam("id") Long id) {
	    return success(chatSessionService.getChatRecord(id));
	}

}