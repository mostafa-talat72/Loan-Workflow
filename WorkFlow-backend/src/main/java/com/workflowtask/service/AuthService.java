package com.workflowtask.service;

import com.workflowtask.dto.AuthResponseDTO;
import com.workflowtask.dto.UserAccountLoginDTO;
import com.workflowtask.dto.UserAccountSignupDTO;

public interface AuthService {
    AuthResponseDTO login(UserAccountLoginDTO userAccountLoginDTO);

    AuthResponseDTO signup(UserAccountSignupDTO userAccountSignupDTO);
}
