package com.stonewu.aifusion.module.member.service.config;

import com.stonewu.aifusion.module.member.controller.admin.config.vo.MemberConfigSaveReqVO;
import com.stonewu.aifusion.module.member.dal.dataobject.config.MemberConfigDO;
import jakarta.validation.Valid;

/**
 * 会员配置 Service 接口
 *
 * @author QingX
 */
public interface MemberConfigService {

    /**
     * 保存会员配置
     *
     * @param saveReqVO 更新信息
     */
    void saveConfig(@Valid MemberConfigSaveReqVO saveReqVO);

    /**
     * 获得会员配置
     *
     * @return 积分配置
     */
    MemberConfigDO getConfig();

}
