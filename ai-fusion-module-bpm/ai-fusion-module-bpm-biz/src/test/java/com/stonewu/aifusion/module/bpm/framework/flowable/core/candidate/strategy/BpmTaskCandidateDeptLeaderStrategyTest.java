package com.stonewu.aifusion.module.bpm.framework.flowable.core.candidate.strategy;

import com.stonewu.aifusion.framework.test.core.ut.BaseMockitoUnitTest;
import com.stonewu.aifusion.module.system.api.dept.DeptApi;
import com.stonewu.aifusion.module.system.api.dept.dto.DeptRespDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Set;

import static com.stonewu.aifusion.framework.common.util.collection.SetUtils.asSet;
import static com.stonewu.aifusion.framework.test.core.util.RandomUtils.randomPojo;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class BpmTaskCandidateDeptLeaderStrategyTest extends BaseMockitoUnitTest {

    @InjectMocks
    private BpmTaskCandidateDeptLeaderStrategy strategy;

    @Mock
    private DeptApi deptApi;

    @Test
    public void testCalculateUsers() {
        // 准备参数
        String param = "1,2";
        // mock 方法
        DeptRespDTO dept1 = randomPojo(DeptRespDTO.class, o -> o.setLeaderUserId(11L));
        DeptRespDTO dept2 = randomPojo(DeptRespDTO.class, o -> o.setLeaderUserId(22L));
        when(deptApi.getDeptList(eq(asSet(1L, 2L)))).thenReturn(asList(dept1, dept2));

        // 调用
        Set<Long> results = strategy.calculateUsers(null, param);
        // 断言
        assertEquals(asSet(11L, 22L), results);
    }

}
