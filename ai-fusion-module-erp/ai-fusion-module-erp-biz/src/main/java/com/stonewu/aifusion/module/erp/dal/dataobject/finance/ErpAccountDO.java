package com.stonewu.aifusion.module.erp.dal.dataobject.finance;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.stonewu.aifusion.framework.common.enums.CommonStatusEnum;
import com.stonewu.aifusion.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * ERP 结算账户 DO
 *
 *
 */
@TableName("erp_account")
@KeySequence("erp_account_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErpAccountDO extends BaseDO {

    /**
     * 结算账户编号
     */
    @TableId
    private Long id;
    /**
     * 账户名称
     */
    private String name;
    /**
     * 账户编码
     */
    private String no;
    /**
     * 备注
     */
    private String remark;
    /**
     * 开启状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否默认
     */
    private Boolean defaultStatus;

}