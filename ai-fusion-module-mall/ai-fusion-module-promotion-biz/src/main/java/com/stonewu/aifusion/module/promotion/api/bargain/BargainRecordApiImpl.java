package com.stonewu.aifusion.module.promotion.api.bargain;

import com.stonewu.aifusion.module.promotion.api.bargain.dto.BargainValidateJoinRespDTO;
import com.stonewu.aifusion.module.promotion.service.bargain.BargainRecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 砍价活动 API 实现类
 *
 * @author HUIHUI
 */
@Service
@Validated
public class BargainRecordApiImpl implements BargainRecordApi {

    @Resource
    private BargainRecordService bargainRecordService;

    @Override
    public BargainValidateJoinRespDTO validateJoinBargain(Long userId, Long bargainRecordId, Long skuId) {
        return bargainRecordService.validateJoinBargain(userId, bargainRecordId, skuId);
    }

    @Override
    public void updateBargainRecordOrderId(Long id, Long orderId) {
        bargainRecordService.updateBargainRecordOrderId(id, orderId);
    }

}
