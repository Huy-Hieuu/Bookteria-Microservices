package org.huyhieu.service;

import org.huyhieu.dto.data.UserDto;
import org.huyhieu.dto.request.UserCreateRequest;
import org.huyhieu.dto.request.UserUpdateRequest;

import java.util.List;

/**
 *
 * @author donh
 */
public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUser(Integer id);

    UserDto createUser(UserCreateRequest request);

    UserDto updateUser(Integer id, UserUpdateRequest request);

    void deleteUser(Integer id);
}
