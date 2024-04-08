package com.stonewu.aifusion.module.member.api.config;

import com.stonewu.aifusion.module.member.api.config.dto.MemberConfigRespDTO;
import com.stonewu.aifusion.module.member.convert.config.MemberConfigConvert;
import com.stonewu.aifusion.module.member.service.config.MemberConfigService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 用户配置 API 实现类
 *
 * @author owen
 */
@Service
@Validated
public class MemberConfigApiImpl implements MemberConfigApi {

    @Resource
    private MemberConfigService memberConfigService;

    @Override
    public MemberConfigRespDTO getConfig() {
        return MemberConfigConvert.INSTANCE.convert01(memberConfigService.getConfig());
    }

}
