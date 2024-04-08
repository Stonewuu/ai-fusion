package com.stonewu.aifusion.module.promotion.service.diy;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.module.promotion.controller.admin.diy.vo.page.DiyPageCreateReqVO;
import com.stonewu.aifusion.module.promotion.controller.admin.diy.vo.page.DiyPagePageReqVO;
import com.stonewu.aifusion.module.promotion.controller.admin.diy.vo.page.DiyPagePropertyUpdateRequestVO;
import com.stonewu.aifusion.module.promotion.controller.admin.diy.vo.page.DiyPageUpdateReqVO;
import com.stonewu.aifusion.module.promotion.dal.dataobject.diy.DiyPageDO;
import jakarta.validation.Valid;

import java.util.Collection;
import java.util.List;

/**
 * 装修页面 Service 接口
 *
 * @author owen
 */
public interface DiyPageService {

    /**
     * 创建装修页面
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDiyPage(@Valid DiyPageCreateReqVO createReqVO);

    /**
     * 更新装修页面
     *
     * @param updateReqVO 更新信息
     */
    void updateDiyPage(@Valid DiyPageUpdateReqVO updateReqVO);

    /**
     * 删除装修页面
     *
     * @param id 编号
     */
    void deleteDiyPage(Long id);

    /**
     * 获得装修页面
     *
     * @param id 编号
     * @return 装修页面
     */
    DiyPageDO getDiyPage(Long id);

    /**
     * 获得装修页面列表
     *
     * @param ids 编号
     * @return 装修页面列表
     */
    List<DiyPageDO> getDiyPageList(Collection<Long> ids);

    /**
     * 获得装修页面分页
     *
     * @param pageReqVO 分页查询
     * @return 装修页面分页
     */
    PageResult<DiyPageDO> getDiyPagePage(DiyPagePageReqVO pageReqVO);

    /**
     * 更新装修页面属性
     *
     * @param updateReqVO 更新信息
     */
    void updateDiyPageProperty(DiyPagePropertyUpdateRequestVO updateReqVO);

    /**
     * 获得模板所属的页面列表
     *
     * @param templateId 模板编号
     * @return 装修页面列表
     */
    List<DiyPageDO> getDiyPageByTemplateId(Long templateId);

}
