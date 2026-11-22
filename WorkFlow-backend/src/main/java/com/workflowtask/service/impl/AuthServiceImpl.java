package com.workflowtask.service.impl;

import com.workflowtask.config.security.token.TokenHandler;
import com.workflowtask.dto.AuthResponseDTO;
import com.workflowtask.dto.UserAccountDTO;
import com.workflowtask.dto.UserAccountLoginDTO;
import com.workflowtask.dto.UserAccountSignupDTO;
import com.workflowtask.mapper.UserAccountMapper;
import com.workflowtask.model.UserAccount;
import com.workflowtask.repo.UserAccountRepo;
import com.workflowtask.service.AuthService;
import com.workflowtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final TokenHandler tokenHandler;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO login(UserAccountLoginDTO userAccountLoginDTO) {
        UserAccountDTO userAccountDTO;
        try {
            userAccountDTO = userService.getUserByUsername(userAccountLoginDTO.getUsername());
        }catch (UsernameNotFoundException ex){
            throw new BadCredentialsException("Invalid username or password");
        }

        String rawPassword = userAccountLoginDTO.getPassword();

        if(Objects.isNull(rawPassword)
                || !userAccountDTO.isEnabled()
                || !passwordEncoder.matches(rawPassword, userAccountDTO.getPassword())){
            throw new BadCredentialsException("Invalid username or password");
        }

        return new AuthResponseDTO(
                userAccountLoginDTO.getUsername(),
                tokenHandler.createToken(userAccountDTO),
                userAccountDTO.getRole().getCode()
        );
    }

    @Override
    public AuthResponseDTO signup(UserAccountSignupDTO userAccountSignupDTO) {

        UserAccountDTO userAccountDTO = userService.createUserAccount(userAccountSignupDTO);

        return new AuthResponseDTO(
                userAccountSignupDTO.getUsername(),
                tokenHandler.createToken(userAccountDTO),
                userAccountDTO.getRole().getCode()
        );
    }


}
