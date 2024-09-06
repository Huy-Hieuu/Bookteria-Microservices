package org.huyhieu.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 *
 * @author huyhieu
 */
@Builder(toBuilder = true)
@Getter
@Setter
public class UserUpdateRequest {
    @Size(min = 1, max = 50, message = "User Name must be in [1, 50]")
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
