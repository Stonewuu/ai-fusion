package com.stonewu.aifusion.module.erp.framework.web.config;

import com.stonewu.aifusion.framework.swagger.config.AiFusionSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * erp 模块的 web 组件的 Configuration
 *
 *
 */
@Configuration(proxyBeanMethods = false)
public class ErpWebConfiguration {

    /**
     * erp 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi erpGroupedOpenApi() {
        return AiFusionSwaggerAutoConfiguration.buildGroupedOpenApi("erp");
    }

}
