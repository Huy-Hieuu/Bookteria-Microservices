package org.huyhieu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.huyhieu.core.AbstractAuditableEntity;
import org.huyhieu.enums.RoleEnum;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "identity_role")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AttributeOverride(name = "id", column = @Column(name = "IDENTITY_ROLE_ID"))
public class IdentityRole extends AbstractAuditableEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_TYPE")
    RoleEnum type;

    @ManyToMany(mappedBy = "identityRoles", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"identityRoles"})
    private Set<IdentityUser> identityUsers = new HashSet<>();

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
