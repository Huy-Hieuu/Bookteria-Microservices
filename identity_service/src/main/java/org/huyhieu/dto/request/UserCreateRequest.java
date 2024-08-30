package org.huyhieu.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 *
 * @author donh
 */
@Builder(toBuilder = true)
@Getter
@Setter
public class UserCreateRequest {
    // Passing string enum here because validation can not pass constant string in enum (USERNAME_INVALID.getName)
    @Size(min = 1, max = 50, message = "USERNAME_INVALID")
    @NotNull
    private String username;

    @Size(min = 5, max = 50, message = "PASSWORD_INVALID")
    @NotNull
    private String password;

    @Size(min = 1, max = 50, message = "First Name must be in [1, 50]")
    @NotNull
    private String firstName;

    @Size(min = 1, max = 50, message = "Last Name must be in [1, 50]")
    @NotNull
    private String lastName;

    private LocalDate dob;
}
