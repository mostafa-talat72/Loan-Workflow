package com.workflowtask.service.impl;

import com.workflowtask.dto.RoleDTO;
import com.workflowtask.dto.UserAccountDTO;
import com.workflowtask.mapper.RoleMapper;
import com.workflowtask.mapper.UserAccountMapper;
import com.workflowtask.model.Role;
import com.workflowtask.model.UserAccount;
import com.workflowtask.repo.RoleRepo;
import com.workflowtask.repo.UserAccountRepo;
import com.workflowtask.service.RoleService;
import com.workflowtask.service.UserService;
import jakarta.transaction.SystemException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;
    private final RoleMapper roleMapper;

    @Override
    public Role getRoleEntityById(Long id) {
        return roleRepo.findById(id).orElseThrow(
                () -> new InsufficientAuthenticationException("Role with id: " + id + " not found")
        );
    }

    @Override
    public Role getRoleEntityByCode(String code) {
        return roleRepo.findByCode(code).orElseThrow(
                () -> new InsufficientAuthenticationException("Role with code: " + code + " not found")
        );
    }

    @Override
    public RoleDTO getRoleByCode(String code) {
        return roleMapper.convertFromRoleToRoleDto(
          roleRepo.findByCode(code).orElseThrow(
                  () -> new InsufficientAuthenticationException("Role with code: " + code + " not found")
          )
        );
    }
}
