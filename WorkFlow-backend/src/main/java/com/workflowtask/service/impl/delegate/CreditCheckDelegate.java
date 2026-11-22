package com.workflowtask.service.impl.delegate;

import com.workflowtask.service.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("creditCheckDelegate")
@RequiredArgsConstructor
@Slf4j
public class CreditCheckDelegate implements JavaDelegate {

    private final LoanApplicationService loanApplicationService;
    @Override
    public void execute(DelegateExecution execution) {
        Long applicationId = Long.valueOf(execution.getProcessInstanceBusinessKey());
        int creditScore = loanApplicationService.checkCreditScore(applicationId);
        execution.setVariable("creditScore", creditScore);
    }
}
