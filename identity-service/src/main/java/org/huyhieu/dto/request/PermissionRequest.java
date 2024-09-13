package org.huyhieu.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.huyhieu.enums.PermissionType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest {
    List<PermissionType> types = new ArrayList<>();
}
