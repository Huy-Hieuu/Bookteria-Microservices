package org.huyhieu.dto.data;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.huyhieu.enums.RoleEnum;

import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author donh
 */
@Builder(toBuilder = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdentityUserDto {
    Integer id;
    String username;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<RoleEnum> roles;
}
