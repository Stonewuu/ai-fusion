package com.stonewu.aifusion.module.ai.service.model;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.stonewu.aifusion.module.ai.controller.admin.model.vo.*;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.ModelDO;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.AssistantDO;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.common.util.object.BeanUtils;

import com.stonewu.aifusion.module.ai.dal.mysql.model.ModelMapper;
import com.stonewu.aifusion.module.ai.dal.mysql.model.AssistantMapper;

import static com.stonewu.aifusion.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.stonewu.aifusion.module.ai.enums.ErrorCodeConstants.*;

/**
 * AI模型 Service 实现类
 *
 * @author AiFusion
 */
@Service
@Validated
public class ModelServiceImpl implements ModelService {

    @Resource
    private ModelMapper modelMapper;
    @Resource
    private AssistantMapper assistantMapper;

    @Override
    public Long createModel(ModelSaveReqVO createReqVO) {
        // 插入
        ModelDO model = BeanUtils.toBean(createReqVO, ModelDO.class);
        modelMapper.insert(model);
        // 返回
        return model.getId();
    }

    @Override
    public void updateModel(ModelSaveReqVO updateReqVO) {
        // 校验存在
        validateModelExists(updateReqVO.getId());
        // 更新
        ModelDO updateObj = BeanUtils.toBean(updateReqVO, ModelDO.class);
        modelMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteModel(Long id) {
        // 校验存在
        validateModelExists(id);
        // 删除
        modelMapper.deleteById(id);

        // 删除子表
        deleteAssistantByModelId(id);
    }

    private void validateModelExists(Long id) {
        if (modelMapper.selectById(id) == null) {
            throw exception(MODEL_NOT_EXISTS);
        }
    }

    @Override
    public ModelDO getModel(Long id) {
        return modelMapper.selectById(id);
    }

    @Override
    public PageResult<ModelDO> getModelPage(ModelPageReqVO pageReqVO) {
        return modelMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（AI助手） ====================

    @Override
    public PageResult<AssistantDO> getAssistantPage(PageParam pageReqVO, Long modelId) {
        return assistantMapper.selectPage(pageReqVO, modelId);
    }

    @Override
    public Long createAssistant(AssistantDO assistant) {
        assistantMapper.insert(assistant);
        return assistant.getId();
    }

    @Override
    public void updateAssistant(AssistantDO assistant) {
        // 校验存在
        validateAssistantExists(assistant.getId());
        // 更新
        assistantMapper.updateById(assistant);
    }

    @Override
    public void deleteAssistant(Long id) {
        // 校验存在
        validateAssistantExists(id);
        // 删除
        assistantMapper.deleteById(id);
    }

    @Override
    public AssistantDO getAssistant(Long id) {
        return assistantMapper.selectById(id);
    }

    private void validateAssistantExists(Long id) {
        if (assistantMapper.selectById(id) == null) {
            throw exception(ASSISTANT_NOT_EXISTS);
        }
    }

    private void deleteAssistantByModelId(Long modelId) {
        assistantMapper.deleteByModelId(modelId);
    }

}