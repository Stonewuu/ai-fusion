package com.stonewu.aifusion.framework.apilog.core.service;

import com.stonewu.aifusion.module.infra.api.logger.dto.ApiErrorLogCreateReqDTO;

/**
 * API 错误日志 Framework Service 接口
 *
 *
 */
public interface ApiErrorLogFrameworkService {

    /**
     * 创建 API 错误日志
     *
     * @param reqDTO API 错误日志
     */
    void createApiErrorLog(ApiErrorLogCreateReqDTO reqDTO);

}
