package com.stonewu.aifusion.module.ai.framework.web.config;

import com.stonewu.aifusion.framework.swagger.config.AiFusionSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ai 模块的 web 组件的 Configuration
 *
 *
 */
@Configuration(proxyBeanMethods = false)
public class AiWebConfiguration {

    /**
     * ai 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi aiGroupedOpenApi() {
        return AiFusionSwaggerAutoConfiguration.buildGroupedOpenApi("ai");
    }

}
