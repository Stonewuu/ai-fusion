package com.stonewu.aifusion.module.promotion.controller.admin.article.vo.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 文章分类更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ArticleCategoryUpdateReqVO extends ArticleCategoryBaseVO {

    @Schema(description = "文章分类编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "19490")
    @NotNull(message = "文章分类编号不能为空")
    private Long id;

}
