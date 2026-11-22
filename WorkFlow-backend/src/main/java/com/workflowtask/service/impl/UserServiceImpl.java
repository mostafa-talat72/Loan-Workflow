package com.workflowtask.service.impl;

import com.workflowtask.dto.UserAccountDTO;
import com.workflowtask.dto.UserAccountSignupDTO;
import com.workflowtask.mapper.UserAccountMapper;
import com.workflowtask.model.UserAccount;
import com.workflowtask.repo.UserAccountRepo;
import com.workflowtask.service.RoleService;
import com.workflowtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserAccountRepo userAccountRepo;
    private final UserAccountMapper userAccountMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserAccount getUserEntityById(Long id) {

        return userAccountRepo.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User Account with id: " + id + " not found")
        );
    }

    @Override
    public UserAccount getUserEntityByUsername(String username) {

        return userAccountRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Account with username: " + username + " not found")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccountDTO getUserByUsername(String username) {
        return userAccountMapper.convertFromUserAccountToUserAccountDto(
                userAccountRepo.findByUsername(username).orElseThrow(
                        () -> new UsernameNotFoundException("User Account with username: " + username + " not found")
                )
        );
    }

    @Transactional
    @Override
    public UserAccountDTO createUserAccount(UserAccountSignupDTO userAccountSignupDTO) {

        if (userAccountRepo.existsByUsername(userAccountSignupDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Username " + userAccountSignupDTO.getUsername() + " is already taken");
        }

        UserAccount userAccount = userAccountMapper
                .convertFromUserAccountSignupDtoToUserAccount(userAccountSignupDTO);

        userAccount.setPassword(passwordEncoder.encode(userAccountSignupDTO.getPassword()));
        userAccount.setRole(roleService.getRoleEntityByCode("CUSTOMER"));
        userAccount.setCreditScore(720);
        return userAccountMapper.convertFromUserAccountToUserAccountDto(
                userAccountRepo.save(userAccount)
        );
    }

}
