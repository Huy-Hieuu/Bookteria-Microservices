package org.huyhieu.service;

import org.huyhieu.dto.data.IdentityUserDto;
import org.huyhieu.dto.request.UserCreateRequest;
import org.huyhieu.dto.request.UserUpdateRequest;

import java.util.List;

/**
 *
 * @author donh
 */
public interface UserService {
    List<IdentityUserDto> getAllUsers();

    IdentityUserDto getUser(Integer id);

    IdentityUserDto createUser(UserCreateRequest request);

    IdentityUserDto updateUser(Integer id, UserUpdateRequest request);

    void deleteUser(Integer id);

    IdentityUserDto getMyInfo();
}
