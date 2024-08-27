package org.example.service;

import java.util.List;

import org.example.dto.UserDto;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.request.UserUpdateRequest;

/**
 *
 * @author donh
 */
public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUser(Long id);

    UserDto createUser(UserCreateRequest request);

    UserDto updateUser(Long id, UserUpdateRequest request);

    UserDto deleteUser(Long id);
}
