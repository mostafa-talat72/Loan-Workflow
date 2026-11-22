package com.workflowtask.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Payload for POST /users. The password travels in clear text here and the
// service stores the hash; the profile row owns the FK now, so it is managed
// through the UserDetails resource instead of this payload.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountSignupDTO {

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(max = 100, message = "Password must not exceed 100 characters")
    private String password;

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @Email(message = "Email must be a valid address")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

}
