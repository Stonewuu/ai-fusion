package com.stonewu.aifusion.module.ai.dal.dataobject.chatsession;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.stonewu.aifusion.framework.mybatis.core.dataobject.BaseDO;

/**
 * 对话记录 DO
 *
 * @author AiFusion
 */
@TableName("ai_chat_session")
@KeySequence("ai_chat_session_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSessionDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 助手ID
     */
    private Long assistantId;
    /**
     * 备注
     */
    private String remark;

}