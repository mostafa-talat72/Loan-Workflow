package com.workflowtask.service;

import com.workflowtask.dto.LoanApplicationDTO;
import com.workflowtask.dto.LoanApplicationRequestDTO;
import com.workflowtask.dto.ReviewDecisionDTO;

import java.util.List;

public interface LoanApplicationService {

    LoanApplicationDTO submit(LoanApplicationRequestDTO requestDTO);

    LoanApplicationDTO getById(Long applicationId);

    List<LoanApplicationDTO> myApplications();

    List<LoanApplicationDTO> pendingApplications();

    Integer checkCreditScore(Long applicationId);

    public LoanApplicationDTO riskOfficerReview(Long applicationId, ReviewDecisionDTO reviewDecisionDTO);

    public LoanApplicationDTO managerBranchReview(Long applicationId, ReviewDecisionDTO reviewDecisionDTO);

    public String disburse(Long applicationId);
}
