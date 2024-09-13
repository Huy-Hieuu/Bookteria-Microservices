package org.huyhieu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.huyhieu.dto.data.IdentityUserDto;
import org.huyhieu.dto.request.UserCreateRequest;
import org.huyhieu.dto.request.UserUpdateRequest;
import org.huyhieu.dto.response.APIResponse;
import org.huyhieu.enums.APIStatus;
import org.huyhieu.service.UserService;
import org.huyhieu.utils.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author huyhieu
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    private final UserService userService;

    // @Valid is used to define this object need to valid
    @PostMapping("/create")
    public ResponseEntity<APIResponse<IdentityUserDto>> createUser(@RequestBody @Valid UserCreateRequest request) {
        return ResponseUtils.buildResponseEntity(userService.createUser(request), APIStatus.SUCCESS);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<IdentityUserDto>>> getUsers() {
        return ResponseUtils.buildResponseEntity(userService.getAllUsers(), APIStatus.SUCCESS);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse<IdentityUserDto>> getUser(@PathVariable("userId") Integer id) {
        return ResponseUtils.buildResponseEntity(userService.getUser(id), APIStatus.SUCCESS);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<APIResponse<IdentityUserDto>> updateUser(@PathVariable("userId") Integer id, @RequestBody @Valid UserUpdateRequest request) {
        return ResponseUtils.buildResponseEntity(userService.updateUser(id, request), APIStatus.SUCCESS);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<APIResponse<Void>> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        return ResponseUtils.buildResponseEntity(null, APIStatus.SUCCESS);
    }

    @GetMapping("/my-info")
    public ResponseEntity<APIResponse<IdentityUserDto>> getSelfInfo() {
        return ResponseUtils.buildResponseEntity(userService.getMyInfo(), APIStatus.SUCCESS);
    }
}
