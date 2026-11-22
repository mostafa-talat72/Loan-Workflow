package com.workflowtask.service;

import com.workflowtask.dto.UserAccountDTO;
import com.workflowtask.dto.UserAccountSignupDTO;
import com.workflowtask.model.UserAccount;
import jakarta.transaction.SystemException;

public interface UserService {

    UserAccount getUserEntityById(Long id);

    UserAccount getUserEntityByUsername(String username);

    UserAccountDTO getUserByUsername(String username);

    UserAccountDTO createUserAccount(UserAccountSignupDTO userAccountSignupDTO);

}
