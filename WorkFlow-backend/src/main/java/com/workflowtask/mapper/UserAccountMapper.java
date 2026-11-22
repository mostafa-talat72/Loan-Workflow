package com.workflowtask.mapper;

import com.workflowtask.dto.UserAccountDTO;
import com.workflowtask.dto.UserAccountSignupDTO;
import com.workflowtask.model.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// The nested role row is produced by RoleMapper.
@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserAccountMapper {

    UserAccountDTO convertFromUserAccountToUserAccountDto(UserAccount userAccount);

    List<UserAccountDTO> convertFromUserAccountListToUserAccountDtoList(List<UserAccount> userAccounts);

    // The id is database generated, the role is resolved by the service, the
    // credit score is assigned by the business rule, and the audit/enabled
    // fields are managed by the entity itself.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "creditScore", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserAccount convertFromUserAccountSignupDtoToUserAccount(UserAccountSignupDTO signupDTO);
}
