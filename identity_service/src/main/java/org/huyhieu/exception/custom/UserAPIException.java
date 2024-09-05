package org.huyhieu.exception.custom;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.huyhieu.enums.APIStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAPIException extends RuntimeException {
    int errorCode;

    public UserAPIException(APIStatus apiStatus) {
        super(apiStatus.getMessage());
        this.errorCode = apiStatus.getCode();
    }
}
