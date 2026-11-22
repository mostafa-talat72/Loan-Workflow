package com.workflowtask.helper;

import com.workflowtask.dto.UserAccountDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomUserDetails implements UserDetails {


    private final UserAccountDTO userAccountDTO;

    public CustomUserDetails(UserAccountDTO userAccountDTO) {
        this.userAccountDTO = userAccountDTO;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(Objects.isNull(userAccountDTO.getRole())){
            return List.of();
        }

        return Stream.of(userAccountDTO.getRole().getCode())
                .map(code -> new SimpleGrantedAuthority("ROLE_" + code))
                .collect(Collectors.toSet());

    }

    @Override
    public String getPassword() {
        return userAccountDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userAccountDTO.getUsername();
    }
}
