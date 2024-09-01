package org.huyhieu.controller;

import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.huyhieu.dto.request.AuthenticationRequest;
import org.huyhieu.dto.request.IntrospectionRequest;
import org.huyhieu.dto.response.ApiResponse;
import org.huyhieu.dto.response.AuthenticationResponse;
import org.huyhieu.dto.response.IntrospectionResponse;
import org.huyhieu.service.AuthenticationService;
import org.huyhieu.utils.enums.APIStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/*
* This controller uses to verify user's password
* */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder()
                          .code(APIStatus.SUCCESS.getCode())
                          .message(APIStatus.SUCCESS.getMessage())
                          .result(response)
                          .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectionResponse> introspect(@RequestBody IntrospectionRequest request){
        try {
            IntrospectionResponse response = authenticationService.introspect(request);

            return ApiResponse.<IntrospectionResponse>builder()
                              .result(response)
                              .message(APIStatus.SUCCESS.getMessage())
                              .code(APIStatus.SUCCESS.getCode())
                              .build();

        } catch (JOSEException | ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
