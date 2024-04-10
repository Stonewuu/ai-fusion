package com.stonewu.aifusion.module.bpm.framework.flowable.core.candidate.strategy;

import com.stonewu.aifusion.module.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import com.stonewu.aifusion.module.bpm.framework.flowable.core.enums.BpmTaskCandidateStrategyEnum;
import com.stonewu.aifusion.module.bpm.framework.flowable.core.util.FlowableUtils;
import org.dromara.hutool.core.convert.Convert;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 流程表达式 {@link BpmTaskCandidateStrategy} 实现类
 *
 *
 */
@Component
public class BpmTaskCandidateExpressionStrategy implements BpmTaskCandidateStrategy {

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.EXPRESSION;
    }

    @Override
    public void validateParam(String param) {
        // do nothing 因为它基本做不了校验
    }

    @Override
    public Set<Long> calculateUsers(DelegateExecution execution, String param) {
        Object result = FlowableUtils.getExpressionValue(execution, param);
        return Convert.toSet(Long.class, result);
    }

}