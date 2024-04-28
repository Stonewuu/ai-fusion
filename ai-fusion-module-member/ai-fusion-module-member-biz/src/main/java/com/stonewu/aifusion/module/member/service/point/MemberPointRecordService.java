package com.stonewu.aifusion.module.member.service.point;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.module.member.controller.admin.point.vo.recrod.MemberPointRecordPageReqVO;
import com.stonewu.aifusion.module.member.controller.app.point.vo.AppMemberPointRecordPageReqVO;
import com.stonewu.aifusion.module.member.dal.dataobject.point.MemberPointRecordDO;
import com.stonewu.aifusion.module.member.enums.point.MemberPointBizTypeEnum;

/**
 * 用户积分记录 Service 接口
 *
 * @author QingX
 */
public interface MemberPointRecordService {

    /**
     * 【管理员】获得积分记录分页
     *
     * @param pageReqVO 分页查询
     * @return 签到记录分页
     */
    PageResult<MemberPointRecordDO> getPointRecordPage(MemberPointRecordPageReqVO pageReqVO);

    /**
     * 【会员】获得积分记录分页
     *
     * @param userId 用户编号
     * @param pageReqVO 分页查询
     * @return 签到记录分页
     */
    PageResult<MemberPointRecordDO> getPointRecordPage(Long userId, AppMemberPointRecordPageReqVO pageReqVO);

    /**
     * 创建用户积分记录
     *
     * @param userId              用户ID
     * @param point               变动积分
     * @param bizType             业务类型
     * @param bizId               业务编号
     * @param allowResultNegative 允许本次变动积分结果为负数
     */
    void createPointRecord(Long userId, Integer point, MemberPointBizTypeEnum bizType, String bizId, boolean allowResultNegative);
}
