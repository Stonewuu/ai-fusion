package com.stonewu.aifusion.module.ai.service.model;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.stonewu.aifusion.module.ai.controller.admin.model.vo.*;
import com.stonewu.aifusion.module.ai.dal.dataobject.model.ModelDO;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.common.util.object.BeanUtils;

import com.stonewu.aifusion.module.ai.dal.mysql.model.ModelMapper;

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
    public void deleteModel(Long id) {
        // 校验存在
        validateModelExists(id);
        // 删除
        modelMapper.deleteById(id);
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

}