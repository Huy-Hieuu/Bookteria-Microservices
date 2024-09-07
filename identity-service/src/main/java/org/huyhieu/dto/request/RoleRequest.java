package org.huyhieu.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.huyhieu.enums.PermissionType;
import org.huyhieu.enums.RoleType;

import java.util.Set;

@Getter
@Setter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    RoleType type;
    Set<PermissionType> permissionTypes;
}
