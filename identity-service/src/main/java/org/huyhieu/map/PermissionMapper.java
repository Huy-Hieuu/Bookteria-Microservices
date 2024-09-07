package org.huyhieu.map;

import org.huyhieu.dto.request.PermissionRequest;
import org.huyhieu.dto.response.PermissionResponse;
import org.huyhieu.entity.IdentityPermission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface PermissionMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    IdentityPermission toIdentityPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(IdentityPermission permission);

    List<PermissionResponse> toPermissionResponses(List<IdentityPermission> permissions);
}
