package com.workflowtask.config;

import com.workflowtask.model.Decision;
import com.workflowtask.model.LoanStatus;
import com.workflowtask.model.Role;
import com.workflowtask.model.UserAccount;
import com.workflowtask.repo.DecisionRepo;
import com.workflowtask.repo.LoanStatusRepo;
import com.workflowtask.repo.RoleRepo;
import com.workflowtask.repo.UserAccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

// Seeds the lookup rows the workflow needs; every insert is guarded by its
// code so restarts never duplicate rows.
@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final RoleRepo roleRepo;
    private final LoanStatusRepo loanStatusRepo;
    private final DecisionRepo decisionRepo;
    private final UserAccountRepo userAccountRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedRoles();
        seedStatuses();
        seedDecisions();
        seedUsers();
    }

    private void seedRoles() {
        seedRole("CUSTOMER", "Customer");
        seedRole("RISK_OFFICER", "Risk Officer");
        seedRole("BRANCH_MANAGER", "Branch Manager");
        log.info("APP_ROLE rows: {}", roleRepo.count());
    }

    private void seedRole(String code, String name) {
        if (roleRepo.existsByCode(code)) {
            return;
        }
        Role role = new Role();
        role.setCode(code);
        role.setName(name);
        roleRepo.save(role);
    }

    private void seedStatuses() {
        seedStatus("SUBMITTED", "Submitted", 1);
        seedStatus("CREDIT_CHECK", "Credit Check", 2);
        seedStatus("OFFICER_REVIEW", "Risk Officer Review", 3);
        seedStatus("MANAGER_REVIEW", "Manager Review", 4);
        seedStatus("APPROVED", "Approved", 5);
        seedStatus("REJECTED", "Rejected", 6);
        log.info("LOAN_STATUS rows: {}", loanStatusRepo.count());
    }

    private void seedStatus(String code, String name, int sortOrder) {
        if (loanStatusRepo.existsByCode(code)) {
            return;
        }
        LoanStatus status = new LoanStatus();
        status.setCode(code);
        status.setName(name);
        status.setSortOrder(sortOrder);
        loanStatusRepo.save(status);
    }

    private void seedDecisions() {
        seedDecision("APPROVE", "Approve");
        seedDecision("REJECT", "Reject");
        log.info("DECISION rows: {}", decisionRepo.count());
    }

    private void seedDecision(String code, String name) {
        if (decisionRepo.existsByCode(code)) {
            return;
        }
        Decision decision = new Decision();
        decision.setCode(code);
        decision.setName(name);
        decisionRepo.save(decision);
    }

    private void seedUsers() {
        seedUser("customer1", "Customer One", "customer1@example.com", "CUSTOMER", 700);
        seedUser("customer2", "Customer Two", "customer2@example.com", "CUSTOMER", 480);
        seedUser("officer1", "Risk Officer One", "officer1@example.com", "RISK_OFFICER", 700);
        seedUser("manager1", "Branch Manager One", "manager1@example.com", "BRANCH_MANAGER", 700);
        log.info("APP_USER rows: {}", userAccountRepo.count());
    }

    private void seedUser(String username, String fullName, String email, String roleCode, int creditScore) {
        Optional<UserAccount> existing = userAccountRepo.findByUsername(username);
        if (existing.isPresent()) {
            UserAccount user = existing.get();
            if (Objects.isNull(user.getCreditScore())) {
                user.setCreditScore(creditScore);
                userAccountRepo.save(user);
            }
            return;
        }
        Role role = roleRepo.findByCode(roleCode).orElseThrow(
                () -> new IllegalStateException("Role " + roleCode + " was not seeded")
        );
        UserAccount user = new UserAccount();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("Password@123"));
        user.setFullName(fullName);
        user.setEmail(email);
        user.setRole(role);
        user.setCreditScore(creditScore);
        userAccountRepo.save(user);
    }
}
