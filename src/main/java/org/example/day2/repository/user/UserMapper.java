package org.example.day2.repository.user;

import org.apache.catalina.mapper.Mapper;
import org.example.day2.model.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("uid"),
                rs.getString("username"),
                rs.getString("password")
        );
    }
}
