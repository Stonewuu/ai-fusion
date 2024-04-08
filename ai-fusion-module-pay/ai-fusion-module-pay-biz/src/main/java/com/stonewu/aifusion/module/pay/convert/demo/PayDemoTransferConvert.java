package com.stonewu.aifusion.module.pay.convert.demo;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferCreateReqVO;
import com.stonewu.aifusion.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferRespVO;
import com.stonewu.aifusion.module.pay.dal.dataobject.demo.PayDemoTransferDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author jason
 */
@Mapper
public interface PayDemoTransferConvert {

    PayDemoTransferConvert INSTANCE = Mappers.getMapper(PayDemoTransferConvert.class);

    PayDemoTransferDO convert(PayDemoTransferCreateReqVO bean);

    PageResult<PayDemoTransferRespVO> convertPage(PageResult<PayDemoTransferDO> pageResult);
}
