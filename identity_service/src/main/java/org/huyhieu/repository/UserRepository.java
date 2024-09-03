package org.huyhieu.repository;

import org.huyhieu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
