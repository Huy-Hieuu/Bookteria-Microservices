package org.huyhieu.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.huyhieu.dto.response.ApiResponse;
import org.huyhieu.enums.APIStatus;
import org.huyhieu.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/*
* This class helps to customize apiStatus when getting UNAUTHORIZED
* (User try to access the protected api but no valid authentication credentials)
* */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ApiResponse<Void> apiResponse = ResponseUtils.buildApiResponse(null, APIStatus.UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //Force to send response to client
        response.flushBuffer();
    }
}
