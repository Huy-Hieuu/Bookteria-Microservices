package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.request.UserUpdateRequest;
import org.example.dto.response.ApiResponse;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author donh
 */
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // @Valid is used to define this object need to valid
    @PostMapping("/createUser")
    public ApiResponse<UserDto> createUser(@RequestBody @Valid UserCreateRequest request) {
        return ApiResponse.<UserDto>builder()
                .code(1000)
                .message("abc")
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
