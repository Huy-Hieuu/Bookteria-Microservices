package org.huyhieu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.huyhieu.core.AbstractAuditableEntity;
import org.huyhieu.enums.PermissionType;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "identity_permission")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AttributeOverride(name = "id", column = @Column(name = "IDENTITY_PERMISSION_ID"))
public class IdentityPermission extends AbstractAuditableEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "PERMISSION_TYPE")
    PermissionType type;

    @ManyToMany(mappedBy = "identityPermissions", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"identityPermissions"})
    private Set<IdentityRole> identityRoles = new HashSet<>();

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
