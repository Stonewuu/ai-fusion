package com.stonewu.aifusion.module.erp.dal.dataobject.stock;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.stonewu.aifusion.framework.common.enums.CommonStatusEnum;
import com.stonewu.aifusion.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * ERP 仓库 DO
 *
 *
 */
@TableName("erp_warehouse")
@KeySequence("erp_warehouse_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErpWarehouseDO extends BaseDO {

    /**
     * 仓库编号
     */
    @TableId
    private Long id;
    /**
     * 仓库名称
     */
    private String name;
    /**
     * 仓库地址
     */
    private String address;
    /**
     * 排序
     */
    private Long sort;
    /**
     * 备注
     */
    private String remark;
    /**
     * 负责人
     */
    private String principal;
    /**
     * 仓储费，单位：元
     */
    private BigDecimal warehousePrice;
    /**
     * 搬运费，单位：元
     */
    private BigDecimal truckagePrice;
    /**
     * 开启状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 是否默认
     */
    private Boolean defaultStatus;

}