package org.huyhieu.repository;

import org.huyhieu.entity.IdentityUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author donh
 */
@Repository
public interface UserRepositoryCustom {
    IdentityUser saveUser(IdentityUser identityUser);
    List<IdentityUser> getUsers();
    IdentityUser updateUser(IdentityUser identityUser);
    void deleteUser(Long userId);
    IdentityUser findUserById(Integer userId);
}
