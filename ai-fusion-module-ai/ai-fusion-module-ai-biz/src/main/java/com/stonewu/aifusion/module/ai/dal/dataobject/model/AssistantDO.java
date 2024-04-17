package com.stonewu.aifusion.module.ai.dal.dataobject.model;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.stonewu.aifusion.framework.mybatis.core.dataobject.BaseDO;

/**
 * AI助手 DO
 *
 * @author AiFusion
 */
@TableName("ai_assistant")
@KeySequence("ai_assistant_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssistantDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 助手名称
     */
    private String name;
    /**
     * 初始prompt
     */
    private String prompt;
    /**
     * AI模型编号
     */
    private Long modelId;
    /**
     * 打招呼语
     */
    private String notice;
    /**
     * 对话轮次
     */
    private Integer round;
    /**
     * 备注
     */
    private String remark;

}