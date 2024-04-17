package com.stonewu.aifusion.module.erp.controller.admin.sale.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - ERP 销售订单新增/修改 Request VO")
@Data
public class ErpSaleOrderSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17386")
    private Long id;

    @Schema(description = "客户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1724")
    @NotNull(message = "客户编号不能为空")
    private Long customerId;

    @Schema(description = "下单时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "下单时间不能为空")
    private LocalDateTime orderTime;

    @Schema(description = "销售员编号", example = "1888")
    private Long saleUserId;

    @Schema(description = "结算账户编号", example = "31189")
    private Long accountId;

    @Schema(description = "优惠率，百分比", requiredMode = Schema.RequiredMode.REQUIRED, example = "99.88")
    private BigDecimal discountPercent;

    @Schema(description = "定金金额，单位：元", example = "7127")
    private BigDecimal depositPrice;

    @Schema(description = "附件地址", example = "https://www.stonewu.com")
    private String fileUrl;

    @Schema(description = "备注", example = "")
    private String remark;

    @Schema(description = "订单清单列表")
    private List<Item> items;

    @Data
    public static class Item {

        @Schema(description = "订单项编号", example = "11756")
        private Long id;

        @Schema(description = "产品编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3113")
        @NotNull(message = "产品编号不能为空")
        private Long productId;

        @Schema(description = "产品单位单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "3113")
        @NotNull(message = "产品单位单位不能为空")
        private Long productUnitId;

        @Schema(description = "产品单价", example = "100.00")
        private BigDecimal productPrice;

        @Schema(description = "产品数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.00")
        @NotNull(message = "产品数量不能为空")
        private BigDecimal count;

        @Schema(description = "税率，百分比", example = "99.88")
        private BigDecimal taxPercent;

        @Schema(description = "备注", example = "")
        private String remark;

    }

}