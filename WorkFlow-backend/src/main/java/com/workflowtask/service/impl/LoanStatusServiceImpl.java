package com.workflowtask.service.impl;

import com.workflowtask.model.LoanStatus;
import com.workflowtask.repo.LoanStatusRepo;
import com.workflowtask.service.LoanStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoanStatusServiceImpl implements LoanStatusService {

    private final LoanStatusRepo loanStatusRepo;

    @Override
    public LoanStatus getByCode(String code) {
        return loanStatusRepo.findByCode(code).orElseThrow(
                () -> new IllegalStateException("Loan status '" + code + "' is not seeded")
        );
    }
}
