package com.stonewu.aifusion.ssodemo.controller;

import cn.hutool.core.util.StrUtil;
import com.stonewu.aifusion.ssodemo.client.OAuth2Client;
import com.stonewu.aifusion.ssodemo.client.dto.CommonResult;
import com.stonewu.aifusion.ssodemo.client.dto.oauth2.OAuth2AccessTokenRespDTO;
import com.stonewu.aifusion.ssodemo.framework.core.util.SecurityUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private OAuth2Client oauth2Client;

    /**
     * 使用刷新令牌，获得（刷新）访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 访问令牌；注意，实际项目中，最好创建对应的 ResponseVO 类，只返回必要的字段
     */
    @PostMapping("/refresh-token")
    public CommonResult<OAuth2AccessTokenRespDTO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return oauth2Client.refreshToken(refreshToken);
    }

    /**
     * 退出登录
     *
     * @param request 请求
     * @return 成功
     */
    @PostMapping("/logout")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = SecurityUtils.obtainAuthorization(request, "Authorization");
        if (StrUtil.isNotBlank(token)) {
            return oauth2Client.revokeToken(token);
        }
        // 返回成功
        return new CommonResult<>();
    }

}
