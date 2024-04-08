package com.stonewu.aifusion.module.trade.controller.admin.delivery.vo.express;

import com.alibaba.excel.annotation.ExcelProperty;
import com.stonewu.aifusion.framework.excel.core.annotations.DictFormat;
import com.stonewu.aifusion.framework.excel.core.convert.DictConvert;
import com.stonewu.aifusion.module.system.enums.DictTypeConstants;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 快递公司 Excel VO
 */
@Data
public class DeliveryExpressExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("快递公司编码")
    private String code;

    @ExcelProperty("快递公司名称")
    private String name;

    @ExcelProperty("快递公司 logo")
    private String logo;

    @ExcelProperty("排序")
    private Integer sort;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
