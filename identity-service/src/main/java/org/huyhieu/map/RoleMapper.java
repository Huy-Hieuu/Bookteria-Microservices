package org.huyhieu.map;

import org.huyhieu.dto.request.RoleRequest;
import org.huyhieu.dto.response.RoleResponse;
import org.huyhieu.entity.IdentityRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapperConfig.class, uses = PermissionMapper.class)
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "identityPermissions", ignore = true)
    IdentityRole toIdentityRole(RoleRequest request);

    @Mapping(target = "permissionResponses", ignore = true)
    RoleResponse toRoleResponse(IdentityRole role);
}
