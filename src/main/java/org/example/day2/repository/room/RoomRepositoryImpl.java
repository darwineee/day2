package org.example.day2.repository.room;

import lombok.RequiredArgsConstructor;
import org.example.day2.core.model.room.Room;
import org.example.day2.core.repository.room.RoomRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {

    private final JdbcClient jdbcClient;

    @Override
    public List<Room> findAll() {
        var sql = "select * from rooms";
        return jdbcClient.sql(sql)
                .query(Room.class)
                .list();
    }

    @Transactional
    @Override
    public Optional<Room> findById(int id) {
        var sql = "select * from rooms where id = :id";
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(Room.class)
                .optional();
    }
}
