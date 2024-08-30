package org.huyhieu.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.huyhieu.dto.request.AuthenticationRequest;
import org.huyhieu.dto.response.ApiResponse;
import org.huyhieu.dto.response.AuthenticationResponse;
import org.huyhieu.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* This controller uses to verify user's password
* */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        boolean isAuthenticated = authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder()
                          .result(AuthenticationResponse.builder()
                                                        .isAuthenticated(isAuthenticated)
                                                        .build())
                          .build();
    }
}
