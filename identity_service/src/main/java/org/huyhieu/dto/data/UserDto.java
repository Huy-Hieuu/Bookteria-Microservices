package org.huyhieu.dto.data;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author donh
 */
@Builder(toBuilder = true)
@Getter
@Setter
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dob;
}
