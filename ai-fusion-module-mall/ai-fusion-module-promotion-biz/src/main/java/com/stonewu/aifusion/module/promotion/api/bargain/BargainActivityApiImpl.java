package com.stonewu.aifusion.module.promotion.api.bargain;

import com.stonewu.aifusion.module.promotion.service.bargain.BargainActivityService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 砍价活动 Api 接口实现类
 *
 * @author HUIHUI
 */
@Service
@Validated
public class BargainActivityApiImpl implements BargainActivityApi {

    @Resource
    private BargainActivityService bargainActivityService;

    @Override
    public void updateBargainActivityStock(Long id, Integer count) {
        bargainActivityService.updateBargainActivityStock(id, count);
    }

}
