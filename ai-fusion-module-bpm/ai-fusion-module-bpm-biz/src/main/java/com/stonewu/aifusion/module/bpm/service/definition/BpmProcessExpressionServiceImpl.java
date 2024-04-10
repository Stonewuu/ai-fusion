package com.stonewu.aifusion.module.bpm.service.definition;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.util.object.BeanUtils;
import com.stonewu.aifusion.module.bpm.controller.admin.definition.vo.expression.BpmProcessExpressionPageReqVO;
import com.stonewu.aifusion.module.bpm.controller.admin.definition.vo.expression.BpmProcessExpressionSaveReqVO;
import com.stonewu.aifusion.module.bpm.dal.dataobject.definition.BpmProcessExpressionDO;
import com.stonewu.aifusion.module.bpm.dal.mysql.definition.BpmProcessExpressionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.stonewu.aifusion.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.stonewu.aifusion.module.bpm.enums.ErrorCodeConstants.PROCESS_EXPRESSION_NOT_EXISTS;

/**
 * BPM 流程表达式 Service 实现类
 *
 *
 */
@Service
@Validated
public class BpmProcessExpressionServiceImpl implements BpmProcessExpressionService {

    @Resource
    private BpmProcessExpressionMapper processExpressionMapper;

    @Override
    public Long createProcessExpression(BpmProcessExpressionSaveReqVO createReqVO) {
        // 插入
        BpmProcessExpressionDO processExpression = BeanUtils.toBean(createReqVO, BpmProcessExpressionDO.class);
        processExpressionMapper.insert(processExpression);
        // 返回
        return processExpression.getId();
    }

    @Override
    public void updateProcessExpression(BpmProcessExpressionSaveReqVO updateReqVO) {
        // 校验存在
        validateProcessExpressionExists(updateReqVO.getId());
        // 更新
        BpmProcessExpressionDO updateObj = BeanUtils.toBean(updateReqVO, BpmProcessExpressionDO.class);
        processExpressionMapper.updateById(updateObj);
    }

    @Override
    public void deleteProcessExpression(Long id) {
        // 校验存在
        validateProcessExpressionExists(id);
        // 删除
        processExpressionMapper.deleteById(id);
    }

    private void validateProcessExpressionExists(Long id) {
        if (processExpressionMapper.selectById(id) == null) {
            throw exception(PROCESS_EXPRESSION_NOT_EXISTS);
        }
    }

    @Override
    public BpmProcessExpressionDO getProcessExpression(Long id) {
        return processExpressionMapper.selectById(id);
    }

    @Override
    public PageResult<BpmProcessExpressionDO> getProcessExpressionPage(BpmProcessExpressionPageReqVO pageReqVO) {
        return processExpressionMapper.selectPage(pageReqVO);
    }

}