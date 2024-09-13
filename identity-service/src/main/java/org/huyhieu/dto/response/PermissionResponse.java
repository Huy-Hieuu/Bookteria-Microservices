package org.huyhieu.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.huyhieu.enums.PermissionType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionResponse {
    List<PermissionType> types = new ArrayList<>();
}
