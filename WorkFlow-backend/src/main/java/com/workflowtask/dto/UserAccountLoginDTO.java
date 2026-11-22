package com.workflowtask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class UserAccountLoginDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
     private String password;

}
