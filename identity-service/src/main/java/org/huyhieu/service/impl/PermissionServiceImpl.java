package org.huyhieu.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.huyhieu.dto.request.PermissionRequest;
import org.huyhieu.dto.response.PermissionResponse;
import org.huyhieu.entity.IdentityPermission;
import org.huyhieu.enums.APIStatus;
import org.huyhieu.enums.PermissionType;
import org.huyhieu.exception.custom.APIException;
import org.huyhieu.map.PermissionMapper;
import org.huyhieu.repository.PermissionRepository;
import org.huyhieu.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;

    @Override
    public PermissionResponse createPermissions(PermissionRequest request) {
        List<IdentityPermission> identityPermissions = PermissionMapper.INSTANCE.mapTypesToIdentityPermissions(request.getTypes());
        identityPermissions = permissionRepository.saveAll(identityPermissions);

        return PermissionMapper.INSTANCE.toPermissionResponse(identityPermissions);
    }

    @Override
    public PermissionResponse getAllPermissions() {
        List<IdentityPermission> permissions = permissionRepository.findAll();

        return PermissionMapper.INSTANCE.toPermissionResponse(permissions);
    }

    @Override
    public void deletePermission(PermissionType permissionType) {
        IdentityPermission permission = permissionRepository.findByType(permissionType)
                .orElseThrow(() -> new APIException(APIStatus.PERMISSION_NOT_EXISTED));

        permissionRepository.delete(permission);
    }
}
