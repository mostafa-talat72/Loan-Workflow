package com.workflowtask.repo;

import com.workflowtask.model.LoanApplication;
import com.workflowtask.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanApplicationRepo extends JpaRepository<LoanApplication, Long> {

    List<LoanApplication> findByApplicant_UsernameOrderByCreatedAtDesc(String username);

    List<LoanApplication> findByStatus(LoanStatus status);

    Optional<LoanApplication> findByProcessInstanceId(String processInstanceId);
}
