package org.huyhieu.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder(toBuilder = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    String username;
    String password;
}
