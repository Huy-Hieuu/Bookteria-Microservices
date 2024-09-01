package org.huyhieu.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.huyhieu.dto.response.ApiResponse;
import org.huyhieu.exception.custom.UserAPIException;
import org.huyhieu.utils.constants.Constants;
import org.huyhieu.utils.enums.APIStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Optional;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException exception) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                                             .code(1009)
                                             .message("Run Time Exception " + exception.getMessage())
                                             .build();

        log.error("Run Time Exception occurred: ", exception);

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = UserAPIException.class)
    ResponseEntity<ApiResponse<Object>> handleUserAPIException(UserAPIException exception) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                                                     .code(exception.getErrorCode())
                                                     .message(exception.getMessage())
                                                     .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    // Catch exception from @Valid in Spring validation
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Object>> handleNotValidException(MethodArgumentNotValidException exception) {
        String enumKey = Optional.ofNullable(exception.getFieldError())
                                 .map(FieldError::getDefaultMessage)
                                 .orElse(Constants.EMPTY_STRING);

        APIStatus apiStatus;
        try {
            apiStatus = APIStatus.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            apiStatus = APIStatus.ILLEGAL_ARGUMENT;

            log.error(Arrays.toString(e.getStackTrace()));
        }

        ApiResponse<Object> apiResponse = ApiResponse.builder()
                                                     .code(apiStatus.getCode())
                                                     .message(apiStatus.getMessage())
                                                     .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
