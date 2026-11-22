package com.workflowtask.repo;

import com.workflowtask.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanStatusRepo extends JpaRepository<LoanStatus, Long> {

    Optional<LoanStatus> findByCode(String code);

    boolean existsByCode(String code);
}
