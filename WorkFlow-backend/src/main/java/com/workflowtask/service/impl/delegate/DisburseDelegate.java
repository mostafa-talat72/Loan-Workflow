package com.workflowtask.service.impl.delegate;

import com.workflowtask.service.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("disburseDelegate")
@RequiredArgsConstructor
@Slf4j
public class DisburseDelegate implements JavaDelegate {

    private final LoanApplicationService loanApplicationService;
    @Override
    public void execute(DelegateExecution execution) {
        Long applicationId = Long.valueOf(execution.getProcessInstanceBusinessKey());
        String disbursementRef = loanApplicationService.disburse(applicationId);
        execution.setVariable("disbursementRef", disbursementRef);
    }
}
