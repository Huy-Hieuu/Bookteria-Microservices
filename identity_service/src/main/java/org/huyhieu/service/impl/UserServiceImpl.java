package org.huyhieu.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.huyhieu.dto.data.UserDto;
import org.huyhieu.dto.request.UserCreateRequest;
import org.huyhieu.dto.request.UserUpdateRequest;
import org.huyhieu.entity.User;
import org.huyhieu.exception.custom.UserAPIException;
import org.huyhieu.map.UserMapper;
import org.huyhieu.repository.UserRepository;
import org.huyhieu.service.UserService;
import org.huyhieu.utils.enums.APIStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author donh
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return UserMapper.INSTANCE.toUserDtos(userRepository.findAll());
    }

    @Override
    public UserDto getUser(Long id) {
        return userRepository.findById(id)
                .map(UserMapper.INSTANCE::toUserDto)
                .orElseThrow(() -> new UserAPIException(APIStatus.USER_NOT_FOUND));
    }

    @Override
    public UserDto createUser(UserCreateRequest request) {
        User user = UserMapper.INSTANCE.toUser(request);

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAPIException(APIStatus.USERNAME_EXISTED);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return UserMapper.INSTANCE.toUserDto(userRepository.saveUser(user));
    }

    @Override
    public UserDto updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findUserById(id);

        if (user == null) {
            throw new UserAPIException(APIStatus.USER_NOT_FOUND);
        }

        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());
        
        return UserMapper.INSTANCE.toUserDto(userRepository.updateUser(user));
    }

    @Override
    public UserDto deleteUser(Long id) {
        return null;
    }
}
