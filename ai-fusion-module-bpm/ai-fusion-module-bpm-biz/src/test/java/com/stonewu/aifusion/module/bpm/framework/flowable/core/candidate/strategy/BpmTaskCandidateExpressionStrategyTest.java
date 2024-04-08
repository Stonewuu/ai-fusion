package com.stonewu.aifusion.module.bpm.framework.flowable.core.candidate.strategy;

import com.stonewu.aifusion.framework.test.core.ut.BaseMockitoUnitTest;
import com.stonewu.aifusion.module.bpm.framework.flowable.core.util.FlowableUtils;
import org.flowable.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;

import java.util.Set;

import static com.stonewu.aifusion.framework.common.util.collection.SetUtils.asSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class BpmTaskCandidateExpressionStrategyTest extends BaseMockitoUnitTest {

    @InjectMocks
    private BpmTaskCandidateExpressionStrategy strategy;

    @Test
    public void testCalculateUsers() {
        try (MockedStatic<FlowableUtils> flowableUtilMockedStatic = mockStatic(FlowableUtils.class)) {
            // 准备参数
            String param = "1,2";
            DelegateExecution execution = mock(DelegateExecution.class);
            // mock 方法
            flowableUtilMockedStatic.when(() -> FlowableUtils.getExpressionValue(same(execution), eq(param)))
                    .thenReturn(asSet(1L, 2L));

            // 调用
            Set<Long> results = strategy.calculateUsers(execution, param);
            // 断言
            assertEquals(asSet(1L, 2L), results);
        }
    }

}
