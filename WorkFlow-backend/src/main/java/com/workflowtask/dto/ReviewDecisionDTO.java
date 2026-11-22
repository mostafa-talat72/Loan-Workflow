package com.workflowtask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Payload for completing a review task. The code must match a row in DECISION
// (APPROVE / REJECT) because the BPMN gateway compares it as a string.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDecisionDTO {

    @NotBlank(message = "Decision is required")
    @Size(max = 20, message = "Decision code must not exceed 20 characters")
    private String decisionCode;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
}
