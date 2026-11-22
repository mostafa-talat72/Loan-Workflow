package com.workflowtask.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "LOAN_APPLICATION")
@Getter
@Setter
@NoArgsConstructor
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Applicant is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id", nullable = false)
    private UserAccount applicant;

    @NotNull(message = "Loan amount is required")
    @DecimalMin(value = "100.00", message = "Loan amount must be at least 100.00")
    @Digits(integer = 17, fraction = 2,
            message = "Loan amount must have at most 17 integer digits and 2 decimal places")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "Loan term is required")
    @Min(value = 1, message = "Loan term must be at least 1 month")
    @Max(value = 360, message = "Loan term must not exceed 360 months")
    @Column(nullable = false)
    private Integer termMonths;

    @Size(max = 200, message = "Purpose must not exceed 200 characters")
    @Column(length = 200)
    private String purpose;

    @Min(value = 0, message = "Credit score must not be negative")
    @Max(value = 1000, message = "Credit score must not exceed 1000")
    private Integer creditScore;

    @NotNull(message = "Status is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private LoanStatus status;

    @Size(max = 64, message = "Process instance id must not exceed 64 characters")
    @Column(length = 64)
    private String processInstanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officer_decision_id")
    private Decision officerDecision;

    @Size(max = 500, message = "Officer notes must not exceed 500 characters")
    @Column(length = 500)
    private String officerNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_decision_id")
    private Decision managerDecision;

    @Size(max = 500, message = "Manager notes must not exceed 500 characters")
    @Column(length = 500)
    private String managerNotes;

    @Size(max = 64, message = "Disbursement reference must not exceed 64 characters")
    @Column(length = 64)
    private String disbursementRef;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}
