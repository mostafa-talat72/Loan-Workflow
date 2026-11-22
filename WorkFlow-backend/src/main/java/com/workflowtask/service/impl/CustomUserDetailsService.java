package com.workflowtask.service.impl;

import com.workflowtask.helper.CustomUserDetails;
import com.workflowtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username){
        return new CustomUserDetails(userService.getUserByUsername(username));
    }
}
