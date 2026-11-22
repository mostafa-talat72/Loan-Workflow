package com.workflowtask.mapper;

import com.workflowtask.dto.RoleDTO;
import com.workflowtask.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDTO convertFromRoleToRoleDto(Role role);

    List<RoleDTO> convertFromRoleListToRoleDtoList(List<Role> roles);

    // Turns the role resolved by RoleService back into an entity the user can
    // hold, so the service never builds it from a raw id.
    Role convertFromRoleDtoToRole(RoleDTO roleDTO);
}
