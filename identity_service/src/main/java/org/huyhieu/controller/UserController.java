package org.huyhieu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.huyhieu.dto.data.UserDto;
import org.huyhieu.dto.request.UserCreateRequest;
import org.huyhieu.dto.request.UserUpdateRequest;
import org.huyhieu.dto.response.ApiResponse;
import org.huyhieu.service.UserService;
import org.huyhieu.enums.APIStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author huyhieu
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {
    private final UserService userService;

    // @Valid is used to define this object need to valid
    @PostMapping("/create/user")
    public ApiResponse<UserDto> createUser(@RequestBody @Valid UserCreateRequest request) {
        return ApiResponse.<UserDto>builder()
                .code(APIStatus.SUCCESS.getCode())
                .message(APIStatus.SUCCESS.getMessage())
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public UserDto getUser(@PathVariable("userId") Integer id) {
        return userService.getUser(id);
    }

    @PutMapping("/update/user/{userId}")
    public UserDto updateUser(@PathVariable("userId") Integer id, @RequestBody @Valid UserUpdateRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/delete/user/{userId}")
    public ApiResponse<Void> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);

        return ApiResponse.<Void>builder()
                          .code(APIStatus.SUCCESS.getCode())
                          .message(APIStatus.SUCCESS.getMessage())
                          .build();
    }
}
