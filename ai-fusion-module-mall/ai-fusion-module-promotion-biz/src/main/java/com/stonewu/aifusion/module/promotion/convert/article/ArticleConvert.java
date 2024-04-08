package com.stonewu.aifusion.module.promotion.convert.article;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.module.promotion.controller.admin.article.vo.article.ArticleCreateReqVO;
import com.stonewu.aifusion.module.promotion.controller.admin.article.vo.article.ArticleRespVO;
import com.stonewu.aifusion.module.promotion.controller.admin.article.vo.article.ArticleUpdateReqVO;
import com.stonewu.aifusion.module.promotion.controller.app.article.vo.article.AppArticleRespVO;
import com.stonewu.aifusion.module.promotion.dal.dataobject.article.ArticleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文章管理 Convert
 *
 * @author HUIHUI
 */
@Mapper
public interface ArticleConvert {

    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    ArticleDO convert(ArticleCreateReqVO bean);

    ArticleDO convert(ArticleUpdateReqVO bean);

    ArticleRespVO convert(ArticleDO bean);

    List<ArticleRespVO> convertList(List<ArticleDO> list);

    PageResult<ArticleRespVO> convertPage(PageResult<ArticleDO> page);

    AppArticleRespVO convert01(ArticleDO article);

    PageResult<AppArticleRespVO> convertPage02(PageResult<ArticleDO> articlePage);

    List<AppArticleRespVO> convertList03(List<ArticleDO> articleCategoryListByRecommendHotAndRecommendBanner);

}
