package org.example.day2.repository.room;

import lombok.RequiredArgsConstructor;
import org.example.day2.core.model.room.Room;
import org.example.day2.core.repository.room.RoomRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Room findRoomById(int roomId) {
        var sql = "select * from rooms where id = :roomId";
        return jdbcClient.sql(sql)
                .param("roomId", roomId)
                .query(Room.class)
                .single();
    }

    @Override
    public Room findRoomByIdWithLock(int roomId) {
        var sql = "select * from rooms where id = :roomId for update";
        return jdbcClient.sql(sql)
                .param("roomId", roomId)
                .query(Room.class)
                .single();
    }
}
