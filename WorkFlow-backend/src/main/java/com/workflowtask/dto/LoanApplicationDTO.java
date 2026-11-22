package com.workflowtask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

// Full loan application view: the nested rows are mapped by their own mappers,
// so LoanApplicationMapper must list them in "uses".
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationDTO {

    private Long id;

    private BigDecimal amount;

    private Integer termMonths;

    private String purpose;

    private Integer creditScore;

    private UserAccountDTO applicant;

    private LoanStatusDTO status;

    private String processInstanceId;

    private DecisionDTO officerDecision;

    private String officerNotes;

    private DecisionDTO managerDecision;

    private String managerNotes;

    private String disbursementRef;

    private Instant createdAt;

    private Instant updatedAt;
}
