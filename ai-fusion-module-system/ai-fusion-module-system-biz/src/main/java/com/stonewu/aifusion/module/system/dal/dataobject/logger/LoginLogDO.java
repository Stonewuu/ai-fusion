package com.stonewu.aifusion.module.system.dal.dataobject.logger;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import com.stonewu.aifusion.framework.common.enums.UserTypeEnum;
import com.stonewu.aifusion.framework.mybatis.core.dataobject.BaseDO;
import com.stonewu.aifusion.module.system.enums.logger.LoginLogTypeEnum;
import com.stonewu.aifusion.module.system.enums.logger.LoginResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 登录日志表
 *
 * 注意，包括登录和登出两种行为
 *
 *
 */
@TableName("system_login_log")
@KeySequence("system_login_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoginLogDO extends BaseDO {

    /**
     * 日志主键
     */
    private Long id;
    /**
     * 日志类型
     *
     * 枚举 {@link LoginLogTypeEnum}
     */
    private Integer logType;
    /**
     * 链路追踪编号
     */
    private String traceId;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     *
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * 用户账号
     *
     * 冗余，因为账号可以变更
     */
    private String username;
    /**
     * 登录结果
     *
     * 枚举 {@link LoginResultEnum}
     */
    private Integer result;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 浏览器 UA
     */
    private String userAgent;

}
