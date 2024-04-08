package com.stonewu.aifusion.module.infra.api.logger;

import com.stonewu.aifusion.module.infra.api.logger.dto.ApiErrorLogCreateReqDTO;
import jakarta.validation.Valid;

/**
 * API 错误日志的 API 接口
 *
 * @author 芋道源码
 */
public interface ApiErrorLogApi {

    /**
     * 创建 API 错误日志
     *
     * @param createDTO 创建信息
     */
    void createApiErrorLog(@Valid ApiErrorLogCreateReqDTO createDTO);

}
