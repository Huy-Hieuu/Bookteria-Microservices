package org.huyhieu.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.huyhieu.enums.RoleType;

@Builder(toBuilder = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    RoleType type;
    PermissionResponse permissionResponse;
}
