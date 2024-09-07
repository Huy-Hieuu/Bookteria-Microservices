package org.huyhieu.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.huyhieu.enums.PermissionType;

@Getter
@Setter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest {
    PermissionType type;
}
