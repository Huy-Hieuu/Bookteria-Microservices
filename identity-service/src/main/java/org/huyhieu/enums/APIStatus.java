package org.huyhieu.enums;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum APIStatus {

    // Define enum constants with code and message
    SUCCESS(1000, "Success", HttpStatus.OK),
    UNAUTHORIZED(1001, "Unauthorized access", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(1002, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(1003, "You do not have permission to access this resource!", HttpStatus.FORBIDDEN),

    // USER,
    PASSWORD_INVALID(1004, "Invalid Password", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1005, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1006, "User not found", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(1007, "User not existed", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(1008, "Username existed", HttpStatus.BAD_REQUEST),

    // PERMISSION,
    PERMISSION_NOT_EXISTED(1009, "Permission is not existed", HttpStatus.NOT_FOUND),
    PERMISSION_EXISTED(1010, "Permission is already existed", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(1013, "Permission not found", HttpStatus.NOT_FOUND),

    // ROLE,
    ROLE_EXISTED(1011, "Role is already existed", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1012, "Role not found", HttpStatus.NOT_FOUND),

    INVALID_REQUEST(9996, "Invalid request", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(9997, "Internal server error", HttpStatus.BAD_REQUEST),
    ILLEGAL_ARGUMENT(9998, "Illegal argument in backend", HttpStatus.BAD_REQUEST),
    UNEXPECTED_ERROR(9999, "Unexpected error", HttpStatus.BAD_REQUEST)
    ;

    private static final Map<Integer, APIStatus> API_STATUS_INTEGER_MAP;
    // Fields
    Integer code;
    String message;
    HttpStatus httpStatus;

    // Constructor
    APIStatus(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    // Way 1 to get Enum from code
    static {
        API_STATUS_INTEGER_MAP = Arrays.stream(APIStatus.values()).collect(Collectors.toMap(APIStatus::getCode, value -> value));
    }

    public static APIStatus fromCode(Integer code) {
        return Optional.ofNullable(API_STATUS_INTEGER_MAP.get(code))
                       .orElseThrow(() -> new IllegalArgumentException("No APIStatus found for code: " + code));
    }

    // Way 2 to get Enum from code
    public static APIStatus fromCode2(Integer code) {
        if (code == null) {
            return null;
        }

        return Arrays.stream(APIStatus.values()).filter(value -> value.getCode().equals(code))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("No APIStatus found for code: " + code));
    }

    @Override
    public String toString() {
        return String.format("ApiError{code=%d, message='%s'}", code, message);
    }

}

