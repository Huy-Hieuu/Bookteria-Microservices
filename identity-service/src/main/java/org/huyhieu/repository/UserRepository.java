package org.huyhieu.repository;

import org.huyhieu.entity.IdentityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<IdentityUser, Integer>, UserRepositoryCustom {
    boolean existsByUsername(String username);
    Optional<IdentityUser> findByUsername(String username);
}
