package org.example.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 *
 * @author donh
 */
@Builder(toBuilder = true)
@Getter
@Setter
public class UserUpdateRequest {
    @Size(min = 1, max = 50, message = "Username must be in [1, 50]")
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dob;
}
