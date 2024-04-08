package com.stonewu.aifusion.module.pay.convert.wallet;

import com.stonewu.aifusion.framework.common.pojo.PageResult;
import com.stonewu.aifusion.module.pay.controller.admin.wallet.vo.transaction.PayWalletTransactionRespVO;
import com.stonewu.aifusion.module.pay.dal.dataobject.wallet.PayWalletTransactionDO;
import com.stonewu.aifusion.module.pay.service.wallet.bo.WalletTransactionCreateReqBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayWalletTransactionConvert {

    PayWalletTransactionConvert INSTANCE = Mappers.getMapper(PayWalletTransactionConvert.class);

    PageResult<PayWalletTransactionRespVO> convertPage2(PageResult<PayWalletTransactionDO> page);

    PayWalletTransactionDO convert(WalletTransactionCreateReqBO bean);

}
