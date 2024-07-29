package org.example.day2.repository.user;

import lombok.RequiredArgsConstructor;
import org.example.day2.model.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public @Nullable User getUser(String email) {
        String sql = "select * from users where email = ?";
        var users = jdbcTemplate.query(sql, new UserMapper(), email);
        return users.isEmpty() ? null : users.get(0);
    }
}
