package com.stonewu.aifusion.framework.security.core.service;

import cn.hutool.core.collection.CollUtil;
import com.stonewu.aifusion.framework.security.core.LoginUser;
import com.stonewu.aifusion.framework.security.core.util.SecurityFrameworkUtils;
import com.stonewu.aifusion.module.system.api.permission.PermissionApi;
import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * 默认的 {@link SecurityFrameworkService} 实现类
 *
 *
 */
@AllArgsConstructor
public class SecurityFrameworkServiceImpl implements SecurityFrameworkService {

    private final PermissionApi permissionApi;

    @Override
    public boolean hasPermission(String permission) {
        return hasAnyPermissions(permission);
    }

    @Override
    public boolean hasAnyPermissions(String... permissions) {
        return permissionApi.hasAnyPermissions(SecurityFrameworkUtils.getLoginUserId(), permissions);
    }

    @Override
    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    @Override
    public boolean hasAnyRoles(String... roles) {
        return permissionApi.hasAnyRoles(SecurityFrameworkUtils.getLoginUserId(), roles);
    }

    @Override
    public boolean hasScope(String scope) {
        return hasAnyScopes(scope);
    }

    @Override
    public boolean hasAnyScopes(String... scope) {
        LoginUser user = SecurityFrameworkUtils.getLoginUser();
        if (user == null) {
            return false;
        }
        return CollUtil.containsAny(user.getScopes(), Arrays.asList(scope));
    }

}
