package com.stonewu.aifusion.module.member.service.signin;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.stonewu.aifusion.framework.common.enums.CommonStatusEnum;
import com.stonewu.aifusion.framework.common.pojo.PageParam;
import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.common.util.date.DateUtils;
import com.stonewu.aifusion.framework.common.util.object.ObjectUtils;
import com.stonewu.aifusion.module.member.controller.admin.signin.vo.record.MemberSignInRecordPageReqVO;
import com.stonewu.aifusion.module.member.controller.app.signin.vo.record.AppMemberSignInRecordSummaryRespVO;
import com.stonewu.aifusion.module.member.convert.signin.MemberSignInRecordConvert;
import com.stonewu.aifusion.module.member.dal.dataobject.signin.MemberSignInConfigDO;
import com.stonewu.aifusion.module.member.dal.dataobject.signin.MemberSignInRecordDO;
import com.stonewu.aifusion.module.member.dal.dataobject.user.MemberUserDO;
import com.stonewu.aifusion.module.member.dal.mysql.signin.MemberSignInRecordMapper;
import com.stonewu.aifusion.module.member.enums.MemberExperienceBizTypeEnum;
import com.stonewu.aifusion.module.member.enums.point.MemberPointBizTypeEnum;
import com.stonewu.aifusion.module.member.service.level.MemberLevelService;
import com.stonewu.aifusion.module.member.service.point.MemberPointRecordService;
import com.stonewu.aifusion.module.member.service.user.MemberUserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;

import static com.stonewu.aifusion.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.stonewu.aifusion.framework.common.util.collection.CollectionUtils.convertSet;
import static com.stonewu.aifusion.module.member.enums.ErrorCodeConstants.SIGN_IN_RECORD_TODAY_EXISTS;

/**
 * 签到记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class MemberSignInRecordServiceImpl implements MemberSignInRecordService {

    @Resource
    private MemberSignInRecordMapper signInRecordMapper;
    @Resource
    private MemberSignInConfigService signInConfigService;
    @Resource
    private MemberPointRecordService pointRecordService;
    @Resource
    private MemberLevelService memberLevelService;

    @Resource
    private MemberUserService memberUserService;

    @Override
    public AppMemberSignInRecordSummaryRespVO getSignInRecordSummary(Long userId) {
        // 1. 初始化默认返回信息
        AppMemberSignInRecordSummaryRespVO summary = new AppMemberSignInRecordSummaryRespVO();
        summary.setTotalDay(0);
        summary.setContinuousDay(0);
        summary.setTodaySignIn(false);

        // 2. 获取用户签到的记录数
        Long signCount = signInRecordMapper.selectCountByUserId(userId);
        if (ObjUtil.equal(signCount, 0L)) {
            return summary;
        }
        summary.setTotalDay(signCount.intValue()); // 设置总签到天数

        // 3. 校验当天是否有签到
        MemberSignInRecordDO lastRecord = signInRecordMapper.selectLastRecordByUserId(userId);
        if (lastRecord == null) {
            return summary;
        }
        summary.setTodaySignIn(DateUtils.isToday(lastRecord.getCreateTime()));

        // 4.1 校验今天是否签到，没有签到则直接返回
        if (!summary.getTodaySignIn()) {
            return summary;
        }
        // 4.2 连续签到天数
        summary.setContinuousDay(lastRecord.getDay());
        return summary;
    }

    @Override
    public PageResult<MemberSignInRecordDO> getSignInRecordPage(MemberSignInRecordPageReqVO pageReqVO) {
        // 根据用户昵称查询出用户ids
        Set<Long> userIds = null;
        if (StringUtils.isNotBlank(pageReqVO.getNickname())) {
            List<MemberUserDO> users = memberUserService.getUserListByNickname(pageReqVO.getNickname());
            // 如果查询用户结果为空直接返回无需继续查询
            if (CollUtil.isEmpty(users)) {
                return PageResult.empty();
            }
            userIds = convertSet(users, MemberUserDO::getId);
        }
        // 分页查询
        return signInRecordMapper.selectPage(pageReqVO, userIds);
    }

    @Override
    public PageResult<MemberSignInRecordDO> getSignRecordPage(Long userId, PageParam pageParam) {
        return signInRecordMapper.selectPage(userId, pageParam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberSignInRecordDO createSignRecord(Long userId) {
        // 1. 获取当前用户最近的签到
        MemberSignInRecordDO lastRecord = signInRecordMapper.selectLastRecordByUserId(userId);
        // 1.1. 判断是否重复签到
        validateSigned(lastRecord);

        // 2.1. 获取所有的签到规则
        List<MemberSignInConfigDO> signInConfigs = signInConfigService.getSignInConfigList(CommonStatusEnum.ENABLE.getStatus());
        // 2.2. 组合数据
        MemberSignInRecordDO record = MemberSignInRecordConvert.INSTANCE.convert(userId, lastRecord, signInConfigs);

        // 3. 插入签到记录
        signInRecordMapper.insert(record);

        // 4. 增加积分
        if (!ObjectUtils.equalsAny(record.getPoint(), null, 0)) {
            pointRecordService.createPointRecord(userId, record.getPoint(), MemberPointBizTypeEnum.SIGN, String.valueOf(record.getId()));
        }
        // 5. 增加经验
        if (!ObjectUtils.equalsAny(record.getExperience(), null, 0)) {
            memberLevelService.addExperience(userId, record.getExperience(), MemberExperienceBizTypeEnum.SIGN_IN, String.valueOf(record.getId()));
        }
        return record;
    }

    private void validateSigned(MemberSignInRecordDO signInRecordDO) {
        if (signInRecordDO == null) {
            return;
        }
        if (DateUtils.isToday(signInRecordDO.getCreateTime())) {
            throw exception(SIGN_IN_RECORD_TODAY_EXISTS);
        }
    }

}
