package org.huyhieu.utils.enums;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum APIStatus {

    // Define enum constants with code and message
    SUCCESS(1000, "Success"),
    ILLEGAL_ARGUMENT(9998, "Illegal argument in backend"),
    USER_NOT_FOUND(1001, "User not found"),
    USERNAME_EXISTED(1002, "Username existed"),
    INVALID_REQUEST(1003, "Invalid request"),
    INTERNAL_SERVER_ERROR(1004, "Internal server error"),
    UNAUTHORIZED(1005, "Unauthorized access"),
    USERNAME_INVALID(1006, "Username must be at least 3 characters"),
    PASSWORD_INVALID(1007, "Invalid Password"),
    USER_NOT_EXISTED(1007, "User not existed"),
    UNEXPECTED_ERROR(9999, "Unexpected error")
    ;

    // Fields
    int code;
    String message;

    // Constructor
    APIStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("ApiError{code=%d, message='%s'}", code, message);
    }
}

