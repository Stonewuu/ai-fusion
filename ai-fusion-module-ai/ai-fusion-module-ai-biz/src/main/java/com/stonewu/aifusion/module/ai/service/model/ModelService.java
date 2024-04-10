package com.stonewu.aifusion.module.ai.service.model;

import java.util.*;
import jakarta.validation.*;
import com.stonewu.aifusion.module.ai.controller.admin.model.vo.*;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.ModelDO;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.pojo.PageParam;

/**
 * AI模型 Service 接口
 *
 * @author AiFusion
 */
public interface ModelService {

    /**
     * 创建AI模型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createModel(@Valid ModelSaveReqVO createReqVO);

    /**
     * 更新AI模型
     *
     * @param updateReqVO 更新信息
     */
    void updateModel(@Valid ModelSaveReqVO updateReqVO);

    /**
     * 删除AI模型
     *
     * @param id 编号
     */
    void deleteModel(Long id);

    /**
     * 获得AI模型
     *
     * @param id 编号
     * @return AI模型
     */
    ModelDO getModel(Long id);

    /**
     * 获得AI模型分页
     *
     * @param pageReqVO 分页查询
     * @return AI模型分页
     */
    PageResult<ModelDO> getModelPage(ModelPageReqVO pageReqVO);

}