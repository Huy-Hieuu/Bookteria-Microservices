package org.huyhieu.repository;

import org.huyhieu.entity.IdentityPermission;
import org.huyhieu.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<IdentityPermission, Integer> {
    Optional<IdentityPermission> findByType(PermissionType type);

    List<IdentityPermission> findByTypeIn(Collection<PermissionType> types);
}
