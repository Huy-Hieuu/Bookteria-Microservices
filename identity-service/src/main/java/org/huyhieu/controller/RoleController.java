package org.huyhieu.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.huyhieu.dto.request.RoleRequest;
import org.huyhieu.dto.response.APIResponse;
import org.huyhieu.dto.response.RoleResponse;
import org.huyhieu.enums.APIStatus;
import org.huyhieu.enums.RoleType;
import org.huyhieu.service.RoleService;
import org.huyhieu.utils.ResponseUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping("/create")
    APIResponse<RoleResponse> createRole(@RequestBody RoleRequest request) {
        return ResponseUtils.buildAPIResponse(roleService.createRole(request), APIStatus.SUCCESS);
    }

    @PostMapping("/update")
    APIResponse<RoleResponse> updateRole(@RequestBody RoleRequest request) {
        return ResponseUtils.buildAPIResponse(roleService.updateRole(request), APIStatus.SUCCESS);
    }

    @GetMapping
    APIResponse<List<RoleResponse>> getAllRoles() {
        return ResponseUtils.buildAPIResponse(roleService.getAllRoles(), APIStatus.SUCCESS);
    }

    @DeleteMapping("/{type}")
    APIResponse<Void> deleteRole(@PathVariable RoleType permissionType) {
        return ResponseUtils.buildAPIResponse(null, APIStatus.SUCCESS);
    }
}
