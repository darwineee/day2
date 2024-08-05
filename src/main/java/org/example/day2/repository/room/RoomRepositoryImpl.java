package org.example.day2.repository.room;

import lombok.RequiredArgsConstructor;
import org.example.day2.core.model.room.Room;
import org.example.day2.core.repository.room.RoomRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

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

    @Override
    public Optional<Room> acquireRoomWithLock(int roomId) {
        return jdbcClient.sql("select * from rooms where id = :roomId for share")
                .param("roomId", roomId)
                .query(Room.class)
                .optional();
    }

    @Override
    public void setLock(int roomId, boolean lock) {
        jdbcClient
                .sql("update rooms set on_booking = :lock where id = :roomId")
                .param("lock", lock)
                .param("roomId", roomId)
                .update();
    }
}
