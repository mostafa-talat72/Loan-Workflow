package com.workflowtask.service.impl.provider;

import com.workflowtask.dto.RoleDTO;
import com.workflowtask.dto.UserAccountDTO;
import com.workflowtask.helper.CustomUserDetails;
import com.workflowtask.service.UserService;
import jakarta.transaction.SystemException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserAccountDTO userAccountDTO = userService.getUserByUsername(username);

        if(Objects.isNull(userAccountDTO)){
            throw new UsernameNotFoundException("User Account with username: " + username + " not found");
        }

        if(!passwordEncoder.matches(password, userAccountDTO.getPassword())){
            throw new BadCredentialsException("Invalid username or password");
        }

        if(Objects.isNull(userAccountDTO.getRole())){
            throw new InsufficientAuthenticationException("Role not found");
        }

        return new UsernamePasswordAuthenticationToken(
                new CustomUserDetails(userAccountDTO),
                userAccountDTO.getPassword(),
                getAuthorities(userAccountDTO.getRole())
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private Set<SimpleGrantedAuthority> getAuthorities(RoleDTO role){
        return Stream.of(role)
                .map(roleDTO -> new SimpleGrantedAuthority("ROLE_" + roleDTO.getCode()))
                .collect(Collectors.toSet());
    }
}
