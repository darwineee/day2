package org.example.day2.core.repository.user;

import org.example.day2.core.model.user.User;

import java.util.Optional;

public interface UserRepository {
    boolean isUserExist(String identity);
    Optional<User> findByIdentity(String identity);
    void save(User user);
}
