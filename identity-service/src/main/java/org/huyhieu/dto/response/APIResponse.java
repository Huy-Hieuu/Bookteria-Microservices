package org.huyhieu.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//Json Include => include non_null fields to json when responsing
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder(toBuilder = true)
public class APIResponse<T> {
    private int code;
    private String message;
    private T result;
}