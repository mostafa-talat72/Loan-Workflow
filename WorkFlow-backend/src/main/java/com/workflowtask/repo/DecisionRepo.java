package com.workflowtask.repo;

import com.workflowtask.model.Decision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DecisionRepo extends JpaRepository<Decision, Long> {

    Optional<Decision> findByCode(String code);

    boolean existsByCode(String code);
}
