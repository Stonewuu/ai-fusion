package com.stonewu.aifusion.module.product.convert.spu;

import com.stonewu.aifusion.framework.common.util.collection.CollectionUtils;
import com.stonewu.aifusion.framework.common.util.object.BeanUtils;
import com.stonewu.aifusion.module.product.controller.admin.spu.vo.ProductSkuRespVO;
import com.stonewu.aifusion.module.product.controller.admin.spu.vo.ProductSpuPageReqVO;
import com.stonewu.aifusion.module.product.controller.admin.spu.vo.ProductSpuRespVO;
import com.stonewu.aifusion.module.product.controller.app.spu.vo.AppProductSpuPageReqVO;
import com.stonewu.aifusion.module.product.dal.dataobject.sku.ProductSkuDO;
import com.stonewu.aifusion.module.product.dal.dataobject.spu.ProductSpuDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static com.stonewu.aifusion.framework.common.util.collection.CollectionUtils.convertMultiMap;

/**
 * 商品 SPU Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductSpuConvert {

    ProductSpuConvert INSTANCE = Mappers.getMapper(ProductSpuConvert.class);

    ProductSpuPageReqVO convert(AppProductSpuPageReqVO bean);

    default ProductSpuRespVO convert(ProductSpuDO spu, List<ProductSkuDO> skus) {
        ProductSpuRespVO spuVO = BeanUtils.toBean(spu, ProductSpuRespVO.class);
        spuVO.setSkus(BeanUtils.toBean(skus, ProductSkuRespVO.class));
        return spuVO;
    }

    default List<ProductSpuRespVO> convertForSpuDetailRespListVO(List<ProductSpuDO> spus, List<ProductSkuDO> skus) {
        Map<Long, List<ProductSkuDO>> skuMultiMap = convertMultiMap(skus, ProductSkuDO::getSpuId);
        return CollectionUtils.convertList(spus, spu -> convert(spu, skuMultiMap.get(spu.getId())));
    }

}
