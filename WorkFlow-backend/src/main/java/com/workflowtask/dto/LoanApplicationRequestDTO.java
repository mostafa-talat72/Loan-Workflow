package com.workflowtask.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

// Payload for submitting a loan application. The applicant is taken from the
// authenticated user, so no username/id is sent here.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationRequestDTO {

    @NotNull(message = "Loan amount is required")
    @DecimalMin(value = "100.00", message = "Loan amount must be at least 100.00")
    @Digits(integer = 17, fraction = 2,
            message = "Loan amount must have at most 17 integer digits and 2 decimal places")
    private BigDecimal amount;

    @NotNull(message = "Loan term is required")
    @Min(value = 1, message = "Loan term must be at least 1 month")
    @Max(value = 360, message = "Loan term must not exceed 360 months")
    private Integer termMonths;

    @Size(max = 200, message = "Purpose must not exceed 200 characters")
    private String purpose;
}
