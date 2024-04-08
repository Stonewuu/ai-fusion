package com.stonewu.aifusion.module.pay.convert.transfer;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import com.stonewu.aifusion.module.pay.api.transfer.dto.PayTransferCreateReqDTO;
import com.stonewu.aifusion.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferCreateReqVO;
import com.stonewu.aifusion.module.pay.controller.admin.transfer.vo.PayTransferCreateReqVO;
import com.stonewu.aifusion.module.pay.controller.admin.transfer.vo.PayTransferPageItemRespVO;
import com.stonewu.aifusion.module.pay.controller.admin.transfer.vo.PayTransferRespVO;
import com.stonewu.aifusion.module.pay.dal.dataobject.transfer.PayTransferDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayTransferConvert {

    PayTransferConvert INSTANCE = Mappers.getMapper(PayTransferConvert.class);

    PayTransferDO convert(PayTransferCreateReqDTO dto);

    PayTransferUnifiedReqDTO convert2(PayTransferDO dto);

    PayTransferCreateReqDTO convert(PayTransferCreateReqVO vo);

    PayTransferCreateReqDTO convert(PayDemoTransferCreateReqVO vo);

    PayTransferRespVO convert(PayTransferDO bean);

    PageResult<PayTransferPageItemRespVO> convertPage(PageResult<PayTransferDO> pageResult);
}
