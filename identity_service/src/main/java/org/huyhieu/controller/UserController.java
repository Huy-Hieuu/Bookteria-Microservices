package org.huyhieu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.huyhieu.dto.data.UserDto;
import org.huyhieu.dto.request.UserCreateRequest;
import org.huyhieu.dto.request.UserUpdateRequest;
import org.huyhieu.dto.response.ApiResponse;
import org.huyhieu.service.UserService;
import org.huyhieu.utils.enums.APIStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author huyhieu
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
@Slf4j
public class UserController {
    private final UserService userService;

    // @Valid is used to define this object need to valid
    @PostMapping("/createUser")
    public ApiResponse<UserDto> createUser(@RequestBody @Valid UserCreateRequest request) {
        return ApiResponse.<UserDto>builder()
                .code(APIStatus.SUCCESS.getCode())
                .message(APIStatus.SUCCESS.getMessage())
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping("/getAllUsers")
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable("userId") Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/updateUser/{userId}")
    public UserDto updateUser(@PathVariable("userId") Long id, @RequestBody @Valid UserUpdateRequest request) {
        return userService.updateUser(id, request);
    }
}
