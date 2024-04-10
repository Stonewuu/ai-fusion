package com.stonewu.aifusion.module.system.framework.datapermission.config;

import com.stonewu.aifusion.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import com.stonewu.aifusion.module.system.dal.dataobject.dept.DeptDO;
import com.stonewu.aifusion.module.system.dal.dataobject.user.AdminUserDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的数据权限 Configuration
 *
 *
 */
@Configuration(proxyBeanMethods = false)
public class DataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer sysDeptDataPermissionRuleCustomizer() {
        return rule -> {
            // dept
            rule.addDeptColumn(AdminUserDO.class);
            rule.addDeptColumn(DeptDO.class, "id");
            // user
            rule.addUserColumn(AdminUserDO.class, "id");
        };
    }

}
