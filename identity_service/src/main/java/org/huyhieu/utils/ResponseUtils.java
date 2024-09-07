package org.huyhieu.utils;

import org.huyhieu.dto.response.ApiResponse;
import org.huyhieu.enums.APIStatus;
import org.springframework.http.ResponseEntity;

/*
* Make final and declare private NoArgsConstructor.
* => Make sure it can not be inherited
* */
public class ResponseUtils {

    private ResponseUtils() {
        // Utility class => should not be instantiated
    }

    public static <T> ResponseEntity<ApiResponse<T>> buildResponseEntity(T result, APIStatus apiStatus) {
        ApiResponse<T> apiResponse = buildApiResponse(result, apiStatus);

        return ResponseEntity.status(apiStatus.getHttpStatus()).body(apiResponse);
    }

    public static <T> ApiResponse<T> buildApiResponse(T result, APIStatus apiStatus) {
        return ApiResponse.<T>builder()
                          .code(apiStatus.getCode())
                          .message(apiStatus.getMessage())
                          .result(result)
                          .build();
    }
}
