package com.stonewu.aifusion.module.erp.dal.mysql.statistics;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ERP 采购统计 Mapper
 *
 *
 */
@Mapper
public interface ErpPurchaseStatisticsMapper {

    BigDecimal getPurchasePrice(@Param("beginTime") LocalDateTime beginTime,
                                @Param("endTime") LocalDateTime endTime);

}
