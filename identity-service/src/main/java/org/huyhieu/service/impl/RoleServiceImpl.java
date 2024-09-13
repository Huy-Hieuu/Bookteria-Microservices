package org.huyhieu.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.huyhieu.dto.request.RoleRequest;
import org.huyhieu.dto.response.RoleResponse;
import org.huyhieu.entity.IdentityPermission;
import org.huyhieu.entity.IdentityRole;
import org.huyhieu.enums.APIStatus;
import org.huyhieu.enums.PermissionType;
import org.huyhieu.enums.RoleType;
import org.huyhieu.exception.custom.APIException;
import org.huyhieu.map.RoleMapper;
import org.huyhieu.repository.PermissionRepository;
import org.huyhieu.repository.RoleRepository;
import org.huyhieu.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImpl implements RoleService {
    PermissionRepository permissionRepository;
    RoleRepository roleRepository;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.findByType(request.getType()).isPresent()){
            throw new APIException(APIStatus.ROLE_EXISTED);
        }
        IdentityRole role = RoleMapper.INSTANCE.toIdentityRole(request);

        // Add existing permissions to role
        List<IdentityPermission> existingPermissions = permissionRepository.findByTypeIn(request.getPermissionTypes());
        role.addPermissions(existingPermissions);

        Map<PermissionType, IdentityPermission> permissionTypeMap = existingPermissions.stream()
                                                                                       .collect(Collectors.toMap(
                                                                                               IdentityPermission::getType,
                                                                                               permission -> permission
                                                                                       ));
        // Create new permissions if they don't already exist
        for (PermissionType permissionType : request.getPermissionTypes()) {
            if (permissionTypeMap.get(permissionType) == null) {
                IdentityPermission newPermission = new IdentityPermission();
                newPermission.setType(permissionType);
                role.addPermission(newPermission);
            }
        }

        role = roleRepository.save(role);

        return RoleMapper.INSTANCE.toRoleResponse(role);
    }

    @Override
    public RoleResponse updateRole(RoleRequest request) {
        IdentityRole role = roleRepository.findByType(request.getType()).orElseThrow(() -> new APIException(APIStatus.ROLE_NOT_FOUND));

        // Remove existing permissions from role
        role.getIdentityPermissions().clear();

        // Update
        List<IdentityPermission> permissions = permissionRepository.findByTypeIn(request.getPermissionTypes());
        role.addPermissions(permissions);
        role = roleRepository.save(role);

        return RoleMapper.INSTANCE.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<IdentityRole> roles = roleRepository.findAll();
        if (!roles.isEmpty()) {
            log.info("Get all roles: {}", roles);
        } else {
            log.info("No roles found");
        }

        return RoleMapper.INSTANCE.toRoleResponses(roles);
    }

    @Override
    public void deleteRole(RoleType type) {
        IdentityRole role = roleRepository.findByType(type).orElseThrow(() -> new APIException(APIStatus.ROLE_NOT_FOUND));
        roleRepository.delete(role);
    }
}
