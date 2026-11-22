package com.workflowtask.controller;

import com.workflowtask.dto.LoanApplicationDTO;
import com.workflowtask.dto.LoanApplicationRequestDTO;
import com.workflowtask.dto.ReviewDecisionDTO;
import com.workflowtask.service.LoanApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanApplicationService loanApplicationService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/submit")
    public ResponseEntity<LoanApplicationDTO> submit(@Valid @RequestBody LoanApplicationRequestDTO requestDTO) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(loanApplicationService.submit(requestDTO));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my")
    public ResponseEntity<List<LoanApplicationDTO>> myApplications() {
        return ResponseEntity.ok(loanApplicationService.myApplications());
    }

    @PreAuthorize("hasAnyRole('RISK_OFFICER', 'BRANCH_MANAGER')")
    @GetMapping("/pending")
    public ResponseEntity<List<LoanApplicationDTO>> pendingApplications() {
        return ResponseEntity.ok(loanApplicationService.pendingApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplicationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(loanApplicationService.getById(id));
    }

    @PreAuthorize("hasRole('RISK_OFFICER')")
    @PostMapping("/{id}/risk-review")
    public ResponseEntity<LoanApplicationDTO> riskReview(@PathVariable Long id,
                                                         @Valid @RequestBody ReviewDecisionDTO reviewDecisionDTO) {
        return ResponseEntity.ok(loanApplicationService.riskOfficerReview(id, reviewDecisionDTO));
    }

    @PreAuthorize("hasRole('BRANCH_MANAGER')")
    @PostMapping("/{id}/manager-review")
    public ResponseEntity<LoanApplicationDTO> managerReview(@PathVariable Long id,
                                                            @Valid @RequestBody ReviewDecisionDTO reviewDecisionDTO) {
        return ResponseEntity.ok(loanApplicationService.managerBranchReview(id, reviewDecisionDTO));
    }

}
