package com.stonewu.aifusion.module.product.convert.favorite;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.util.collection.CollectionUtils;
import com.stonewu.aifusion.module.product.controller.admin.favorite.vo.ProductFavoriteRespVO;
import com.stonewu.aifusion.module.product.controller.app.favorite.vo.AppFavoriteRespVO;
import com.stonewu.aifusion.module.product.dal.dataobject.favorite.ProductFavoriteDO;
import com.stonewu.aifusion.module.product.dal.dataobject.spu.ProductSpuDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.stonewu.aifusion.framework.common.util.collection.CollectionUtils.convertMap;

@Mapper
public interface ProductFavoriteConvert {

    ProductFavoriteConvert INSTANCE = Mappers.getMapper(ProductFavoriteConvert.class);

    ProductFavoriteDO convert(Long userId, Long spuId);

    @Mapping(target = "id", source = "favorite.id")
    @Mapping(target = "spuName", source = "spu.name")
    AppFavoriteRespVO convert(ProductSpuDO spu, ProductFavoriteDO favorite);

    default List<AppFavoriteRespVO> convertList(List<ProductFavoriteDO> favorites, List<ProductSpuDO> spus) {
        List<AppFavoriteRespVO> resultList = new ArrayList<>(favorites.size());
        Map<Long, ProductSpuDO> spuMap = convertMap(spus, ProductSpuDO::getId);
        for (ProductFavoriteDO favorite : favorites) {
            ProductSpuDO spuDO = spuMap.get(favorite.getSpuId());
            resultList.add(convert(spuDO, favorite));
        }
        return resultList;
    }

    default PageResult<ProductFavoriteRespVO> convertPage(PageResult<ProductFavoriteDO> pageResult, List<ProductSpuDO> spuList) {
        Map<Long, ProductSpuDO> spuMap = convertMap(spuList, ProductSpuDO::getId);
        List<ProductFavoriteRespVO> voList = CollectionUtils.convertList(pageResult.getList(), favorite -> {
            ProductSpuDO spu = spuMap.get(favorite.getSpuId());
            return convert02(spu, favorite);
        });
        return new PageResult<>(voList, pageResult.getTotal());
    }
    @Mapping(target = "id", source = "favorite.id")
    @Mapping(target = "userId", source = "favorite.userId")
    @Mapping(target = "spuId", source = "favorite.spuId")
    @Mapping(target = "createTime", source = "favorite.createTime")
    ProductFavoriteRespVO convert02(ProductSpuDO spu, ProductFavoriteDO favorite);

}
