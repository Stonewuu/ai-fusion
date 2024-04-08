package com.stonewu.aifusion.module.product.api.comment;

import com.stonewu.aifusion.module.product.api.comment.dto.ProductCommentCreateReqDTO;
import com.stonewu.aifusion.module.product.service.comment.ProductCommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 商品评论 API 实现类
 *
 * @author HUIHUI
 */
@Service
@Validated
public class ProductCommentApiImpl implements ProductCommentApi {

    @Resource
    private ProductCommentService productCommentService;

    @Override
    public Long createComment(ProductCommentCreateReqDTO createReqDTO) {
        return productCommentService.createComment(createReqDTO);
    }

}
