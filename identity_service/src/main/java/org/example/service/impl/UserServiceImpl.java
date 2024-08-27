package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.request.UserUpdateRequest;
import org.example.entity.User;
import org.example.map.UserMapper;
import org.example.repository.UserRepository;
import org.example.service.UserService;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return UserMapper.INSTANCE.toUserDtos(userRepository.findAll());
    }

    @Override
    public UserDto getUser(Long id) {
        return userRepository.findById(id)
                .map(UserMapper.INSTANCE::toUserDto)
                .orElseThrow(() -> new RuntimeException("abc"));
    }

    @Override
    public UserDto createUser(UserCreateRequest request) {
        User user = UserMapper.INSTANCE.toUser(request);

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("user existed");
        }
        return UserMapper.INSTANCE.toUserDto(userRepository.saveUser(user));
    }

    @Override
    public UserDto updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findUserById(id);

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
