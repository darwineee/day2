package org.example.day2.repository.user;

import lombok.RequiredArgsConstructor;
import org.example.day2.core.model.user.User;
import org.example.day2.core.repository.user.UserRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcClient jdbcClient;

    @Transactional
    @Override
    public Optional<User> findByIdentity(String identity) {
        String sql = "select * from users where email = :email and active = true";
        return jdbcClient.sql(sql)
                .param("email", identity)
                .query(User.class)
                .optional();
    }

    @Override
    public void save(User user) {
        String sql = "insert into users (email, password, active) values (:email, :password, :active)";
        jdbcClient.sql(sql)
                .param("email", user.email())
                .param("password", user.password())
                .param("active", user.active())
                .update();
    }
}
