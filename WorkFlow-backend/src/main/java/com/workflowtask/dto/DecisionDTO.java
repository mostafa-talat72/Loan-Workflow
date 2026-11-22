package com.workflowtask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Read model for a decision row (APPROVE / REJECT).
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DecisionDTO {

    private Long id;

    private String code;

    private String name;
}
