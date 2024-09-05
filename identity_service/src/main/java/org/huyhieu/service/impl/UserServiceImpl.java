package org.huyhieu.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.huyhieu.dto.data.UserDto;
import org.huyhieu.dto.request.UserCreateRequest;
import org.huyhieu.dto.request.UserUpdateRequest;
import org.huyhieu.entity.Role;
import org.huyhieu.entity.User;
import org.huyhieu.enums.APIStatus;
import org.huyhieu.enums.RoleEnum;
import org.huyhieu.exception.custom.UserAPIException;
import org.huyhieu.map.UserMapper;
import org.huyhieu.repository.RoleRepository;
import org.huyhieu.repository.UserRepository;
import org.huyhieu.service.UserService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author donh
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    /*
    * PreAutorize
    * => Spring will create proxy in this method
    *   => it will check role before execute this method
    */
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserDto> getAllUsers() {
        // The authentication holds details of who is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username {}", authentication.getName());
        log.info("Roles {}", authentication.getAuthorities());

        List<User> users = userRepository.findAll();

        return UserMapper.INSTANCE.toUserDtos(users);
    }

    /*
    * PostAuthorize => Make sure that user can only get their own details
    * */
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @PostAuthorize("hasRole('ADMIN') || (returnObject.username == authentication.name)")
    @Override
    public UserDto getUser(Integer id) {
        Optional<User> user = userRepository.findById(id);

        return user.map(UserMapper.INSTANCE::toUserDto)
                   .orElseThrow(() -> new UserAPIException(APIStatus.USER_NOT_FOUND));
    }

    @Override
    public UserDto createUser(UserCreateRequest request) {
        User user = UserMapper.INSTANCE.toUser(request);

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAPIException(APIStatus.USERNAME_EXISTED);
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Add role
        Role role = roleRepository.findByType(RoleEnum.USER)
                                  .orElseGet(() -> {
                                      Role newRole = new Role();
                                      newRole.setType(RoleEnum.USER);

                                      return newRole;
                                  });
        user.addRole(role);

        return UserMapper.INSTANCE.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(Integer id, UserUpdateRequest request) {
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
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserAPIException(APIStatus.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UserAPIException(APIStatus.USERNAME_EXISTED));

        return UserMapper.INSTANCE.toUserDto(authenticatedUser);
    }
}
