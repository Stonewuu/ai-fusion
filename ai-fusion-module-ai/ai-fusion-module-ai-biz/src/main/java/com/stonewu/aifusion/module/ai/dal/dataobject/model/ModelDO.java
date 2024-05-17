package com.stonewu.aifusion.module.ai.dal.dataobject.model;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.stonewu.aifusion.framework.mybatis.core.dataobject.BaseDO;

/**
 * AI模型 DO
 *
 * @author AiFusion
 */
@TableName("ai_model")
@KeySequence("ai_model_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
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
     * 枚举 {@link TODO ai_model_type 对应的类}
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