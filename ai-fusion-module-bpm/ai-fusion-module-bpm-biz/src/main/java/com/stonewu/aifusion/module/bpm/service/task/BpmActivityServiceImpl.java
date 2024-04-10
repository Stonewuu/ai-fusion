package com.stonewu.aifusion.module.bpm.service.task;

import com.stonewu.aifusion.module.bpm.controller.admin.task.vo.activity.BpmActivityRespVO;
import com.stonewu.aifusion.module.bpm.convert.task.BpmActivityConvert;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;


/**
 * BPM 活动实例 Service 实现类
 *
 *
 */
@Service
@Slf4j
@Validated
public class BpmActivityServiceImpl implements BpmActivityService {

    @Resource
    private HistoryService historyService;

    @Override
    public List<BpmActivityRespVO> getActivityListByProcessInstanceId(String processInstanceId) {
        List<HistoricActivityInstance> activityList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).list();
        return BpmActivityConvert.INSTANCE.convertList(activityList);
    }

    @Override
    public List<HistoricActivityInstance> getHistoricActivityListByExecutionId(String executionId) {
        return historyService.createHistoricActivityInstanceQuery().executionId(executionId).list();
    }

}
