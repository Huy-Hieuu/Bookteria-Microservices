package org.huyhieu.utils;

import org.huyhieu.dto.response.APIResponse;
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

    public static <T> ResponseEntity<APIResponse<T>> buildResponseEntity(T result, APIStatus apiStatus) {
        APIResponse<T> apiResponse = buildAPIResponse(result, apiStatus);

        return ResponseEntity.status(apiStatus.getHttpStatus()).body(apiResponse);
    }

    public static <T> APIResponse<T> buildAPIResponse(T result, APIStatus apiStatus) {
        return APIResponse.<T>builder()
                          .code(apiStatus.getCode())
                          .message(apiStatus.getMessage())
                          .result(result)
                          .build();
    }
}
