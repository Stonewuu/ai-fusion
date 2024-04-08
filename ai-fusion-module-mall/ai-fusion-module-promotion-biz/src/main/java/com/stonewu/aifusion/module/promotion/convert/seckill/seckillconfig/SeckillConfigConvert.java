package com.stonewu.aifusion.module.promotion.convert.seckill.seckillconfig;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.module.promotion.controller.admin.seckill.vo.config.SeckillConfigCreateReqVO;
import com.stonewu.aifusion.module.promotion.controller.admin.seckill.vo.config.SeckillConfigRespVO;
import com.stonewu.aifusion.module.promotion.controller.admin.seckill.vo.config.SeckillConfigSimpleRespVO;
import com.stonewu.aifusion.module.promotion.controller.admin.seckill.vo.config.SeckillConfigUpdateReqVO;
import com.stonewu.aifusion.module.promotion.controller.app.seckill.vo.config.AppSeckillConfigRespVO;
import com.stonewu.aifusion.module.promotion.dal.dataobject.seckill.SeckillConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 秒杀时段 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface SeckillConfigConvert {

    SeckillConfigConvert INSTANCE = Mappers.getMapper(SeckillConfigConvert.class);

    SeckillConfigDO convert(SeckillConfigCreateReqVO bean);

    SeckillConfigDO convert(SeckillConfigUpdateReqVO bean);

    SeckillConfigRespVO convert(SeckillConfigDO bean);

    List<SeckillConfigRespVO> convertList(List<SeckillConfigDO> list);

    List<SeckillConfigSimpleRespVO> convertList1(List<SeckillConfigDO> list);

    PageResult<SeckillConfigRespVO> convertPage(PageResult<SeckillConfigDO> page);

    List<AppSeckillConfigRespVO> convertList2(List<SeckillConfigDO> list);

    AppSeckillConfigRespVO convert1(SeckillConfigDO filteredConfig);
}
