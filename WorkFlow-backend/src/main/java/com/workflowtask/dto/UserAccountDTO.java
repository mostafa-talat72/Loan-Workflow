package com.workflowtask.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Read model for a user account. The password is never exposed, and the role
// stays a nested DTO so the lazy relation serializes as data.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO {

    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String fullName;

    private String email;

    private RoleDTO role;

    private Integer creditScore;

    private boolean enabled;
}
