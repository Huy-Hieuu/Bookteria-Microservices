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
import org.huyhieu.enums.RoleType;

import java.util.HashSet;
import java.util.List;
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
    RoleType type;

    @ManyToMany(mappedBy = "identityRoles", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"identityRoles"})
    private Set<IdentityUser> identityUsers = new HashSet<>();

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "IDENTITY_ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "IDENTITY_PERMISSION_ID")
    )
    @JsonIgnoreProperties(value = "identityRoles")
    Set<IdentityPermission> identityPermissions = new HashSet<>();

    public void addPermission(IdentityPermission identityPermission) {
        this.identityPermissions.add(identityPermission);
        identityPermission.getIdentityRoles().add(this);
    }

    public void removePermission(IdentityPermission identityPermission) {
        this.identityPermissions.remove(identityPermission);
        identityPermission.getIdentityRoles().remove(this);
    }

    public void addPermissions(List<IdentityPermission> identityPermissions) {
        this.identityPermissions.addAll(identityPermissions);
        identityPermissions.forEach(permission -> permission.getIdentityRoles().add(this));
    }

    public void removePermissions(List<IdentityPermission> identityPermissions) {
        identityPermissions.forEach(this.identityPermissions::remove);
        identityPermissions.forEach(permission -> permission.getIdentityRoles().remove(this));
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
