package org.huyhieu.service;

import org.huyhieu.dto.request.PermissionRequest;
import org.huyhieu.dto.response.PermissionResponse;
import org.huyhieu.enums.PermissionType;

import java.util.List;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest request);

    List<PermissionResponse> getAllPermissions();

    void deletePermission(PermissionType permissionType);
}
