package org.huyhieu.service;

import org.huyhieu.dto.request.RoleRequest;
import org.huyhieu.dto.response.RoleResponse;
import org.huyhieu.enums.RoleType;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest request);

    RoleResponse updateRole(RoleRequest request);


    List<RoleResponse> getAllRoles(RoleRequest request);

    void deleteRole(RoleType type);
}
