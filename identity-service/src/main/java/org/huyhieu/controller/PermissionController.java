package org.huyhieu.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.huyhieu.dto.request.PermissionRequest;
import org.huyhieu.dto.response.APIResponse;
import org.huyhieu.dto.response.PermissionResponse;
import org.huyhieu.enums.APIStatus;
import org.huyhieu.enums.PermissionType;
import org.huyhieu.service.PermissionService;
import org.huyhieu.utils.ResponseUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/permissions")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("/create")
    APIResponse<PermissionResponse> createPermisison(@RequestBody PermissionRequest request) {
        return ResponseUtils.buildAPIResponse(permissionService.createPermissions(request), APIStatus.SUCCESS);
    }

    @GetMapping
    APIResponse<PermissionResponse> getAllPermissions() {
        return ResponseUtils.buildAPIResponse(permissionService.getAllPermissions(), APIStatus.SUCCESS);
    }

    @DeleteMapping("/{type}")
    APIResponse<Void> deletePermission(@PathVariable PermissionType permissionType) {
        return ResponseUtils.buildAPIResponse(null, APIStatus.SUCCESS);
    }
}

