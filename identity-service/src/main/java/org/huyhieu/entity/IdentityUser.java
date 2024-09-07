package org.huyhieu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.huyhieu.core.AbstractAuditableEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author donh
 */
@Entity
@Table(name = "identity_user")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AttributeOverride(name = "id", column = @Column(name = "IDENTITY_USER_ID"))
public class IdentityUser extends AbstractAuditableEntity {
    @Column(name = "[PASSWORD]")
    String password;

    @Column(name = "USER_NAME")
    String username;

    @Column(name = "FIRST_NAME")
    String firstName;

    @Column(name = "LAST_NAME")
    String lastName;

    @Column(name = "[DOB]")
    LocalDate dob;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "IDENTITY_USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "IDENTITY_ROLE_ID")
    )
    @JsonIgnoreProperties(value = "identityUsers")
    Set<IdentityRole> identityRoles = new HashSet<>();

    public void addRole(IdentityRole identityRole) {
        this.identityRoles.add(identityRole);
        identityRole.getIdentityUsers().add(this);
    }

    public void removeRole(IdentityRole identityRole) {
        this.identityRoles.remove(identityRole);
        identityRole.getIdentityUsers().remove(this);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
