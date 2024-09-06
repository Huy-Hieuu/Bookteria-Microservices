package org.huyhieu.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.huyhieu.dto.response.ApiResponse;
import org.huyhieu.enums.APIStatus;
import org.huyhieu.exception.custom.UserAPIException;
import org.huyhieu.utils.ResponseUtils;
import org.huyhieu.utils.ConstantUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException exception) {
        log.error("Run Time Exception occurred: ", exception);

        return ResponseUtils.buildResponseEntity(null, APIStatus.UNEXPECTED_ERROR);
    }

    @ExceptionHandler(value = UserAPIException.class)
    ResponseEntity<ApiResponse<Object>> handleUserAPIException(UserAPIException exception) {
        return ResponseUtils.buildResponseEntity(null, APIStatus.fromCode(exception.getErrorCode()));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(AccessDeniedException exception) {
        return ResponseUtils.buildResponseEntity(null, APIStatus.FORBIDDEN);
    }

    // Catch exception from @Valid in Spring validation
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Object>> handleNotValidException(MethodArgumentNotValidException exception) {
        String enumKey = Optional.ofNullable(exception.getFieldError())
                                 .map(FieldError::getDefaultMessage)
                                 .orElse(ConstantUtils.EMPTY_STRING);

        APIStatus apiStatus;
        try {
            apiStatus = APIStatus.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            apiStatus = APIStatus.ILLEGAL_ARGUMENT;

            log.error("Exception when validation, enumKey not found: ", e);
        }

        return ResponseUtils.buildResponseEntity(null, apiStatus);
    }
}
