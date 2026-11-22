package com.workflowtask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Read model for a loan status row.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatusDTO {

    private Long id;

    private String code;

    private String name;

    private Integer sortOrder;
}
