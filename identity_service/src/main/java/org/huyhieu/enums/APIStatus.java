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
    ILLEGAL_ARGUMENT(9998, "Illegal argument in backend", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED(1002, "Username existed", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST(1003, "Invalid request", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(1004, "Internal server error", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1006, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1007, "Invalid Password", HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(1007, "User not existed", HttpStatus.NOT_FOUND),

    UNAUTHORIZED(1005, "Unauthorized access", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(1008, "Unauthenticated", HttpStatus.UNAUTHORIZED),

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

