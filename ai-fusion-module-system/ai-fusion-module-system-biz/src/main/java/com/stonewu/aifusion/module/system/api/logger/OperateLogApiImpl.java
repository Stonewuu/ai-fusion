package com.stonewu.aifusion.module.system.api.logger;

import com.fhs.core.trans.anno.TransMethodResult;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.util.object.BeanUtils;
import com.stonewu.aifusion.module.system.api.logger.dto.OperateLogCreateReqDTO;
import com.stonewu.aifusion.module.system.api.logger.dto.OperateLogPageReqDTO;
import com.stonewu.aifusion.module.system.api.logger.dto.OperateLogRespDTO;
import com.stonewu.aifusion.module.system.dal.dataobject.logger.OperateLogDO;
import com.stonewu.aifusion.module.system.service.logger.OperateLogService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 操作日志 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private OperateLogService operateLogService;

    @Override
    @Async
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        operateLogService.createOperateLog(createReqDTO);
    }

    @Override
    @TransMethodResult
    public PageResult<OperateLogRespDTO> getOperateLogPage(OperateLogPageReqDTO pageReqVO) {
        PageResult<OperateLogDO> operateLogPage = operateLogService.getOperateLogPage(pageReqVO);
        return BeanUtils.toBean(operateLogPage, OperateLogRespDTO.class);
    }

}
