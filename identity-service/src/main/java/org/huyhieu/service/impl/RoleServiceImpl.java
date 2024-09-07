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
import org.huyhieu.enums.RoleType;
import org.huyhieu.exception.custom.APIException;
import org.huyhieu.map.RoleMapper;
import org.huyhieu.repository.PermissionRepository;
import org.huyhieu.repository.RoleRepository;
import org.huyhieu.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<IdentityPermission> permissions = permissionRepository.findByTypeIn(request.getPermissionTypes());
        role.addPermissions(permissions);

        role = roleRepository.save(role);

        return RoleMapper.INSTANCE.toRoleResponse(role);
    }

    @Override
    public RoleResponse updateRole(RoleRequest request) {
        return null;
    }

    @Override
    public List<RoleResponse> getAllRoles(RoleRequest request) {
        return null;
    }

    @Override
    public void deleteRole(RoleType type) {

    }
}
