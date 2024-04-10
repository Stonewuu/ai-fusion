package com.stonewu.aifusion.module.system.api.logger;

import com.stonewu.aifusion.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.stonewu.aifusion.module.system.service.logger.LoginLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 登录日志的 API 实现类
 *
 *
 */
@Service
@Validated
public class LoginLogApiImpl implements LoginLogApi {

    @Resource
    private LoginLogService loginLogService;

    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        loginLogService.createLoginLog(reqDTO);
    }

}
