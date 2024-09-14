package org.huyhieu.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.huyhieu.validator.DOBConstraint;

import java.time.LocalDate;

/**
 *
 * @author huyhieu
 */
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    // Passing string enum here because validation can not pass constant string in enum (USERNAME_INVALID.name)
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

    @DOBConstraint(minAge = 5, message = "DOB_INVALID")
    private LocalDate dob;
}
