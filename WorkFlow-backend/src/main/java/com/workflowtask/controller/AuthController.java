package com.workflowtask.controller;

import com.workflowtask.dto.AuthResponseDTO;
import com.workflowtask.dto.UserAccountLoginDTO;
import com.workflowtask.dto.UserAccountSignupDTO;
import com.workflowtask.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody UserAccountLoginDTO requestDTO) {
        return ResponseEntity.ok(authService.login(requestDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> signup(@Valid @RequestBody UserAccountSignupDTO requestDTO) {
        return ResponseEntity.ok(authService.signup(requestDTO));
    }

}
