package org.huyhieu.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.huyhieu.dto.request.AuthenticationRequest;
import org.huyhieu.entity.User;
import org.huyhieu.exception.custom.UserAPIException;
import org.huyhieu.repository.UserRepository;
import org.huyhieu.service.AuthenticationService;
import org.huyhieu.utils.enums.APIStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;

    @Override
    public boolean authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                                  .orElseThrow(() -> new UserAPIException(APIStatus.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
}
