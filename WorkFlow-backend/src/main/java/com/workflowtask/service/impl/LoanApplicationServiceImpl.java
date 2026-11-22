package com.workflowtask.service.impl;

import com.workflowtask.dto.LoanApplicationDTO;
import com.workflowtask.dto.LoanApplicationRequestDTO;
import com.workflowtask.dto.ReviewDecisionDTO;
import com.workflowtask.mapper.LoanApplicationMapper;
import com.workflowtask.model.*;
import com.workflowtask.repo.LoanApplicationRepo;
import com.workflowtask.service.DecisionService;
import com.workflowtask.service.LoanApplicationService;
import com.workflowtask.service.LoanStatusService;
import com.workflowtask.service.LoanWorkflowService;
import com.workflowtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepo loanApplicationRepo;
    private final LoanApplicationMapper loanApplicationMapper;
    private final UserService userService;
    private final LoanStatusService loanStatusService;
    private final LoanWorkflowService loanWorkflowService;
    private final DecisionService decisionService;

    @Transactional
    @Override
    public LoanApplicationDTO submit(LoanApplicationRequestDTO requestDTO) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        UserAccount userAccount = userService.getUserEntityByUsername(username);

        if (!"CUSTOMER".equals(userAccount.getRole().getCode())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Role " + userAccount.getRole().getCode() + " cannot submit applications");
        }

        LoanApplication requestApplication = loanApplicationMapper
                .convertFromLoanApplicationRequestDtoToLoanApplication(requestDTO);

        requestApplication.setApplicant(userAccount);

        LoanStatus loanStatus = loanStatusService.getByCode("SUBMITTED");

        requestApplication.setStatus(loanStatus);

        requestApplication = loanApplicationRepo.save(requestApplication);

        String processInstanceId = loanWorkflowService.start(requestApplication);

        requestApplication.setProcessInstanceId(processInstanceId);

        Task submitTask = loanWorkflowService.getTask(processInstanceId);
        if (Objects.isNull(submitTask) || !"submitApplication".equals(submitTask.getTaskDefinitionKey())) {
            throw new IllegalStateException("Process did not start at the submission task");
        }

        loanWorkflowService.completeTask(submitTask.getId(), Map.of());

        syncStatus(requestApplication);
        return loanApplicationMapper.convertFromLoanApplicationToLoanApplicationDto(
                loanApplicationRepo.save(requestApplication)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public LoanApplicationDTO getById(Long applicationId) {
        LoanApplication loanApplication = findLoanApplicationOrThrow(applicationId);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAccount userAccount = userService.getUserEntityByUsername(username);

        if ("CUSTOMER".equals(userAccount.getRole().getCode())
                && !username.equals(loanApplication.getApplicant().getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You can only view your own applications");
        }

        return loanApplicationMapper.convertFromLoanApplicationToLoanApplicationDto(loanApplication);
    }

    @Transactional(readOnly = true)
    @Override
    public List<LoanApplicationDTO> myApplications() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return loanApplicationRepo.findByApplicant_UsernameOrderByCreatedAtDesc(username).stream()
                .map(loanApplicationMapper::convertFromLoanApplicationToLoanApplicationDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<LoanApplicationDTO> pendingApplications() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String roleCode = userService.getUserEntityByUsername(username).getRole().getCode();

        return loanWorkflowService.pendingTasks(roleCode).stream()
                .map(task -> loanApplicationRepo.findByProcessInstanceId(task.getProcessInstanceId())
                        .map(loanApplicationMapper::convertFromLoanApplicationToLoanApplicationDto)
                        .orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    @Transactional
    @Override
    public Integer checkCreditScore(Long applicationId) {
        LoanApplication loanApplication = findLoanApplicationOrThrow(applicationId);
        Integer creditScore = loanApplication.getApplicant().getCreditScore();
        if (Objects.isNull(creditScore)) {
            throw new IllegalStateException(
                    "Applicant " + loanApplication.getApplicant().getUsername() + " has no credit score");
        }
        loanApplication.setCreditScore(creditScore);
        loanApplicationRepo.save(loanApplication);
        return creditScore;
    }

    @Transactional
    @Override
    public LoanApplicationDTO riskOfficerReview(Long applicationId, ReviewDecisionDTO reviewDecisionDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        UserAccount userAccount = userService.getUserEntityByUsername(username);

        Role role = userAccount.getRole();

        if (!"RISK_OFFICER".equals(role.getCode())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Role " + role.getCode() + " cannot review applications");
        }

        LoanApplication loanApplication = findLoanApplicationOrThrow(applicationId);

        String processInstanceId = loanApplication.getProcessInstanceId();

        if (Objects.isNull(processInstanceId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Application " + applicationId + " has no running process");
        }

        Task riskReviewTask = loanWorkflowService
                .getTaskByRole(processInstanceId, "riskReview", "RISK_OFFICER");

        if (Objects.isNull(riskReviewTask)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No RISK_OFFICER task is waiting for application " + applicationId);
        }

        Decision decision = decisionService.getByCode(reviewDecisionDTO.getDecisionCode());

        loanWorkflowService.completeTask(riskReviewTask.getId(), Map.of("officerDecision", decision.getCode()));

        loanApplication.setOfficerDecision(decision);
        loanApplication.setOfficerNotes(reviewDecisionDTO.getNotes());

        syncStatus(loanApplication);
        return loanApplicationMapper.convertFromLoanApplicationToLoanApplicationDto(
                loanApplicationRepo.save(loanApplication)
        );
    }

    @Transactional
    @Override
    public LoanApplicationDTO managerBranchReview(Long applicationId, ReviewDecisionDTO reviewDecisionDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        UserAccount userAccount = userService.getUserEntityByUsername(username);

        Role role = userAccount.getRole();

        if (!"BRANCH_MANAGER".equals(role.getCode())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Role " + role.getCode() + " cannot review applications");
        }
        LoanApplication loanApplication = findLoanApplicationOrThrow(applicationId);

        String processInstanceId = loanApplication.getProcessInstanceId();

        if (Objects.isNull(processInstanceId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Application " + applicationId + " has no running process");
        }

        Task managerApprovalTask = loanWorkflowService
                .getTaskByRole(processInstanceId, "managerApproval", "BRANCH_MANAGER");
        if (Objects.isNull(managerApprovalTask)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No BRANCH_MANAGER task is waiting for application " + applicationId);
        }

        Decision decision = decisionService.getByCode(reviewDecisionDTO.getDecisionCode());

        loanWorkflowService.completeTask(managerApprovalTask.getId(), Map.of("managerDecision", decision.getCode()));

        loanApplication.setManagerDecision(decision);
        loanApplication.setManagerNotes(reviewDecisionDTO.getNotes());

        syncStatus(loanApplication);

        return loanApplicationMapper.convertFromLoanApplicationToLoanApplicationDto(
                loanApplicationRepo.save(loanApplication)
        );
    }

    @Transactional
    @Override
    public String disburse(Long applicationId) {
        String disbursementRef = "DIS-" + String.valueOf(new Date());

        LoanApplication loanApplication = findLoanApplicationOrThrow(applicationId);

        loanApplication.setDisbursementRef(disbursementRef);
        loanApplicationRepo.save(loanApplication);

        return disbursementRef;
    }

    private void syncStatus(LoanApplication loanApplication) {
        String processInstanceId = loanApplication.getProcessInstanceId();

        if (loanWorkflowService.isFinished(processInstanceId)) {
            String endEvent = loanWorkflowService.currentEndEvent(processInstanceId);
            String statusCode = "approved".equals(endEvent) ? "APPROVED" : "REJECTED";
            loanApplication.setStatus(loanStatusService.getByCode(statusCode));
            return;
        }

        Task currentTask = loanWorkflowService.getTask(processInstanceId);
        if (Objects.isNull(currentTask)) {
            return;
        }

        switch (currentTask.getTaskDefinitionKey()) {
            case "riskReview" -> loanApplication.setStatus(loanStatusService.getByCode("OFFICER_REVIEW"));
            case "managerApproval" -> loanApplication.setStatus(loanStatusService.getByCode("MANAGER_REVIEW"));
            default -> loanApplication.setStatus(loanStatusService.getByCode("SUBMITTED"));
        }
    }

    private LoanApplication findLoanApplicationOrThrow(Long applicationId) {
        return loanApplicationRepo.findById(applicationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Loan Application with id: " + applicationId + " not found")
        );
    }
}
