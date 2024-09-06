package org.huyhieu.repository;

import org.huyhieu.entity.IdentityRole;
import org.huyhieu.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<IdentityRole, Long> {
    Optional<IdentityRole> findByType(RoleEnum type);
}
