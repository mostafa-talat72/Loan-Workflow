package com.workflowtask.service;

import com.workflowtask.dto.RoleDTO;
import com.workflowtask.dto.UserAccountDTO;
import com.workflowtask.model.Role;
import com.workflowtask.model.UserAccount;
import jakarta.transaction.SystemException;

public interface RoleService {

    Role getRoleEntityById(Long id);

    Role getRoleEntityByCode(String code);

    RoleDTO getRoleByCode(String code);

}
