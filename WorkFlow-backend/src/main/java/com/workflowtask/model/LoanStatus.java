package com.workflowtask.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LOAN_STATUS")
@Getter
@Setter
@NoArgsConstructor
public class LoanStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Status code is required")
    @Size(max = 30, message = "Status code must not exceed 30 characters")
    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @NotBlank(message = "Status name is required")
    @Size(max = 50, message = "Status name must not exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String name;

    @NotNull(message = "Status sort order is required")
    @Min(value = 1, message = "Status sort order must be at least 1")
    @Column(nullable = false)
    private Integer sortOrder;
}
