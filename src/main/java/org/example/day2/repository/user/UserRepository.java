package org.example.day2.repository.user;

import lombok.RequiredArgsConstructor;
import org.example.day2.model.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public User getUser(String email) {
        String sql = "select * from users where email = ?";
        return jdbcTemplate.queryForObject(sql, new UserMapper(), email);
    }

    public void insertUser(User user) {
        String sql = "insert into users (email, password) values (?, ?)";
        jdbcTemplate.update(sql, user.email(), user.password());
    }
}
