package org.huyhieu.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder(toBuilder = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    @Builder.Default
    boolean isAuthenticated = false;
    String token;
}
