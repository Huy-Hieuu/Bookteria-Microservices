package org.huyhieu.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.huyhieu.dto.data.IdentityUserDto;
import org.huyhieu.dto.request.UserCreateRequest;
import org.huyhieu.dto.request.UserUpdateRequest;
import org.huyhieu.entity.IdentityRole;
import org.huyhieu.entity.IdentityUser;
import org.huyhieu.enums.APIStatus;
import org.huyhieu.enums.RoleType;
import org.huyhieu.exception.custom.APIException;
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
    *
    * hasRole only works with scopes has prefix ROLE_
    */
    //    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('APPROVE_POST')")
    @Override
    public List<IdentityUserDto> getAllUsers() {
        // The authentication holds details of who is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username {}", authentication.getName());
        log.info("Roles {}", authentication.getAuthorities());

        List<IdentityUser> identityUsers = userRepository.findAll();

        return UserMapper.INSTANCE.toUserDtos(identityUsers);
    }

    /*
    * PostAuthorize => Make sure that user can only get their own details
    * */
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @PostAuthorize("hasRole('ADMIN') || (returnObject.username == authentication.name)")
    @Override
    public IdentityUserDto getUser(Integer id) {
        Optional<IdentityUser> user = userRepository.findById(id);

        return user.map(UserMapper.INSTANCE::toUserDto)
                   .orElseThrow(() -> new APIException(APIStatus.USER_NOT_FOUND));
    }

    @Override
    public IdentityUserDto createUser(UserCreateRequest request) {
        IdentityUser identityUser = UserMapper.INSTANCE.toUser(request);

        if (userRepository.existsByUsername(identityUser.getUsername())) {
            throw new APIException(APIStatus.USERNAME_EXISTED);
        }

        identityUser.setPassword(passwordEncoder.encode(request.getPassword()));

        // Add role
        IdentityRole identityRole = roleRepository.findByType(RoleType.USER)
                                                  .orElseGet(() -> {
                                                      IdentityRole newIdentityRole = new IdentityRole();
                                                      newIdentityRole.setType(RoleType.USER);

                                                      return newIdentityRole;
                                                  });
        identityUser.addRole(identityRole);

        return UserMapper.INSTANCE.toUserDto(userRepository.save(identityUser));
    }

    @Override
    public IdentityUserDto updateUser(Integer id, UserUpdateRequest request) {
        IdentityUser identityUser = userRepository.findById(id).orElseThrow(() -> new APIException(APIStatus.USER_NOT_FOUND));

        identityUser.setUsername(request.getUsername());
        identityUser.setFirstName(request.getFirstName());
        identityUser.setLastName(request.getLastName());
        identityUser.setDob(request.getDob());
        
        return UserMapper.INSTANCE.toUserDto(userRepository.updateUser(identityUser));
    }

    @Override
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new APIException(APIStatus.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    @Override
    public IdentityUserDto getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        IdentityUser authenticatedIdentityUser = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new APIException(APIStatus.USERNAME_EXISTED));

        return UserMapper.INSTANCE.toUserDto(authenticatedIdentityUser);
    }
}
