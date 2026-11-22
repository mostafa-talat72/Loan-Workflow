package com.workflowtask.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DECISION")
@Getter
@Setter
@NoArgsConstructor
public class Decision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Decision code is required")
    @Size(max = 20, message = "Decision code must not exceed 20 characters")
    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @NotBlank(message = "Decision name is required")
    @Size(max = 50, message = "Decision name must not exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String name;
}
