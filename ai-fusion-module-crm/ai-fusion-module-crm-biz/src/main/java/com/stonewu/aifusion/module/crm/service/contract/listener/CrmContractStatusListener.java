package com.stonewu.aifusion.module.crm.service.contract.listener;

import com.stonewu.aifusion.module.bpm.event.BpmProcessInstanceStatusEvent;
import com.stonewu.aifusion.module.bpm.event.BpmProcessInstanceStatusEventListener;
import com.stonewu.aifusion.module.crm.service.contract.CrmContractService;
import com.stonewu.aifusion.module.crm.service.contract.CrmContractServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 合同审批的结果的监听器实现类
 *
 * @author HUIHUI
 */
@Component
public class CrmContractStatusListener extends BpmProcessInstanceStatusEventListener {

    @Resource
    private CrmContractService contractService;

    @Override
    public String getProcessDefinitionKey() {
        return CrmContractServiceImpl.BPM_PROCESS_DEFINITION_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceStatusEvent event) {
        contractService.updateContractAuditStatus(Long.parseLong(event.getBusinessKey()), event.getStatus());
    }

}
