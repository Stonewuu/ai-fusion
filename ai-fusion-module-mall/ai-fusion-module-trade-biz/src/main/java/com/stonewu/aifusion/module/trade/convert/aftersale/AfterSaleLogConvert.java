package com.stonewu.aifusion.module.trade.convert.aftersale;

import com.stonewu.aifusion.module.trade.dal.dataobject.aftersale.AfterSaleLogDO;
import com.stonewu.aifusion.module.trade.service.aftersale.bo.AfterSaleLogCreateReqBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AfterSaleLogConvert {

    AfterSaleLogConvert INSTANCE = Mappers.getMapper(AfterSaleLogConvert.class);

    AfterSaleLogDO convert(AfterSaleLogCreateReqBO bean);

}
