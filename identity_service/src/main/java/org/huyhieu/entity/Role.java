package org.huyhieu.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.huyhieu.core.AbstractAuditableEntity;
import org.huyhieu.enums.RoleEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "role", schema = "identity_service")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AttributeOverride(name = "id", column = @Column(name = "ROLE_ID"))
public class Role extends AbstractAuditableEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_TYPE")
    RoleEnum type;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"roles"})
    private Set<User> users = new HashSet<>();

    // Custom hashCode and equals
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Role role = (Role) obj;
        return Objects.equals(id, role.id);
    }
}
