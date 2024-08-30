package org.huyhieu.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 *
 * @author donh
 */
@Entity
@Table(name = "user", schema = "identity_service")
@Getter
@Setter
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private long id;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "DOB")
    private LocalDate dob;
}
