package com.stonewu.aifusion.framework.pay.core.client.impl;

import com.stonewu.aifusion.framework.pay.core.client.PayClientConfig;
import jakarta.validation.Validator;
import lombok.Data;

/**
 * 无需任何配置 PayClientConfig 实现类
 *
 * @author jason
 */
@Data
public class NonePayClientConfig implements PayClientConfig {

    /**
     * 配置名称
     * <p>
     * 如果不加任何属性，JsonUtils.parseObject2 解析会报错，所以暂时加个名称
     */
    private String name;

    public NonePayClientConfig(){
        this.name = "none-config";
    }

    @Override
    public void validate(Validator validator) {
        // 无任何配置不需要校验
    }
}
