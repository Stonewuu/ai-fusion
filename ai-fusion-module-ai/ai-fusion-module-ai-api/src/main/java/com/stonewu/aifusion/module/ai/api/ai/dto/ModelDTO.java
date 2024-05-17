package com.stonewu.aifusion.module.ai.api.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelDTO {

    /**
     * 编号
     */
    private Long id;
    /**
     * 模型名称
     */
    private String name;
    /**
     * 模型ApiKey
     */
    private String apiKey;
    /**
     * 英文名称
     */
    private String modelName;
    /**
     * 接口基础地址
     */
    private String baseApi;
    /**
     * 模型类型
     *
     */
    private Integer modelType;
    /**
     * 模型价格
     */
    private BigDecimal modelPrice;
    /**
     * 备注
     */
    private String remark;

}
