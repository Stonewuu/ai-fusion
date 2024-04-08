package com.stonewu.aifusion.module.system.api.permission;

import com.stonewu.aifusion.module.system.service.permission.RoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 角色 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class RoleApiImpl implements RoleApi {

    @Resource
    private RoleService roleService;

    @Override
    public void validRoleList(Collection<Long> ids) {
        roleService.validateRoleList(ids);
    }
}
