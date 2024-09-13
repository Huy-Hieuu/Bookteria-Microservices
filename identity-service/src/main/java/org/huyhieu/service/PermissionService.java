package org.huyhieu.service;

import org.huyhieu.dto.request.PermissionRequest;
import org.huyhieu.dto.response.PermissionResponse;
import org.huyhieu.enums.PermissionType;

public interface PermissionService {
    PermissionResponse createPermissions(PermissionRequest request);

    PermissionResponse getAllPermissions();

    void deletePermission(PermissionType permissionType);
}
