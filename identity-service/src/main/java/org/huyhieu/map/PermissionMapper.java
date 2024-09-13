package org.huyhieu.map;

import org.huyhieu.dto.response.PermissionResponse;
import org.huyhieu.entity.IdentityPermission;
import org.huyhieu.enums.PermissionType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface PermissionMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    PermissionResponse toPermissionResponse(IdentityPermission permission);

    default PermissionResponse toPermissionResponse(Collection<IdentityPermission> permissions) {
        PermissionResponse response = new PermissionResponse();
        response.setTypes(permissions.stream().map(IdentityPermission::getType).collect(Collectors.toList()));
        return response;
    }


    default List<IdentityPermission> mapTypesToIdentityPermissions(List<PermissionType> types) {
        return types.stream()
                    .map(this::mapPermissionTypeToIdentityPermission)
                    .collect(Collectors.toList());
    }

    default IdentityPermission mapPermissionTypeToIdentityPermission(PermissionType type) {
        IdentityPermission permission = new IdentityPermission();
        permission.setType(type);
        return permission;
    }
}
