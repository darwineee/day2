package org.example.day2.repository.user;

import lombok.RequiredArgsConstructor;
import org.example.day2.model.user.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcClient jdbcClient;

    public boolean isUserExist(String email) {
        return getUser(email).isPresent();
    }

    public Optional<User> getUser(String email) {
        String sql = "select * from users where email = :email";
        return jdbcClient.sql(sql)
                .param("email", email)
                .query(User.class)
                .optional();
    }

    public void insertUser(User user) {
        String sql = "insert into users (email, password, active) values (:email, :password, :active)";
        jdbcClient.sql(sql)
                .param("email", user.email())
                .param("password", user.password())
                .param("active", user.active())
                .update();
    }
}
