package org.example.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class ApiResponse<T> {
    private int code;
    private String message;
    private T result;
}
