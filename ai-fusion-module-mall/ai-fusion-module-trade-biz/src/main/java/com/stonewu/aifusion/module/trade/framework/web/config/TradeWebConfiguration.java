package com.stonewu.aifusion.module.trade.framework.web.config;

import com.stonewu.aifusion.framework.swagger.config.AiFusionSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * trade 模块的 web 组件的 Configuration
 *
 *
 */
@Configuration(proxyBeanMethods = false)
public class TradeWebConfiguration {

    /**
     * trade 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi tradeGroupedOpenApi() {
        return AiFusionSwaggerAutoConfiguration.buildGroupedOpenApi("trade");
    }

}
