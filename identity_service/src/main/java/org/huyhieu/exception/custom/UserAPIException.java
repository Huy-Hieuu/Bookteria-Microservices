package org.huyhieu.exception.custom;

import lombok.Getter;
import org.huyhieu.utils.enums.APIStatus;

@Getter
public class UserAPIException extends RuntimeException {
    private final int errorCode;

    public UserAPIException(APIStatus apiStatus) {
        super(apiStatus.getMessage());
        this.errorCode = apiStatus.getCode();
    }
}
