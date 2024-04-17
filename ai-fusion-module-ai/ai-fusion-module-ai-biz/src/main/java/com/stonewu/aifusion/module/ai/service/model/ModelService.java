package com.stonewu.aifusion.module.ai.service.model;

import java.util.*;
import jakarta.validation.*;
import com.stonewu.aifusion.module.ai.controller.admin.model.vo.*;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.ModelDO;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.AssistantDO;
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

    // ==================== 子表（AI助手） ====================

    /**
     * 获得AI助手分页
     *
     * @param pageReqVO 分页查询
     * @param modelId AI模型编号
     * @return AI助手分页
     */
    PageResult<AssistantDO> getAssistantPage(PageParam pageReqVO, Long modelId);

    /**
     * 创建AI助手
     *
     * @param assistant 创建信息
     * @return 编号
     */
    Long createAssistant(@Valid AssistantDO assistant);

    /**
     * 更新AI助手
     *
     * @param assistant 更新信息
     */
    void updateAssistant(@Valid AssistantDO assistant);

    /**
     * 删除AI助手
     *
     * @param id 编号
     */
    void deleteAssistant(Long id);

	/**
	 * 获得AI助手
	 *
	 * @param id 编号
     * @return AI助手
	 */
    AssistantDO getAssistant(Long id);

}