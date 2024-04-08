package com.stonewu.aifusion.module.statistics.service.infra;

import com.stonewu.aifusion.module.statistics.dal.mysql.infra.ApiAccessLogStatisticsMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

/**
 * API 访问日志的统计 Service 实现类
 *
 * @author owen
 */
@Service
@Validated
public class ApiAccessLogStatisticsServiceImpl implements ApiAccessLogStatisticsService {

    @Resource
    private ApiAccessLogStatisticsMapper apiAccessLogStatisticsMapper;

    @Override
    public Integer getUserCount(Integer userType, LocalDateTime beginTime, LocalDateTime endTime) {
        return apiAccessLogStatisticsMapper.selectUserCountByUserTypeAndCreateTimeBetween(userType, beginTime, endTime);
    }

    @Override
    public Integer getIpCount(Integer userType, LocalDateTime beginTime, LocalDateTime endTime) {
        return apiAccessLogStatisticsMapper.selectIpCountByUserTypeAndCreateTimeBetween(userType, beginTime, endTime);
    }

}
