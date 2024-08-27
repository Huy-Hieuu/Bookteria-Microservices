package org.example.repository;

import org.example.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author donh
 */
@Repository
public interface UserRepositoryCustom {
    User saveUser(User user);
    List<User> getUsers();
    User updateUser(User user);
    void deleteUser(Long userId);
    User findUserById(Long userId);
}
