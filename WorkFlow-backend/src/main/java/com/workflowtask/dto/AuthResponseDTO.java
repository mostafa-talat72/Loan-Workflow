package com.workflowtask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// Response body for login/signup: the issued JWT. Nothing else from the account
// travels back, so the password hash never leaves the server.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private String username;
    private String token;
    private String role;
}
