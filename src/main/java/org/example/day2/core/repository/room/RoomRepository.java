package org.example.day2.core.repository.room;

import org.example.day2.core.model.room.Room;

import java.util.List;

public interface RoomRepository {
    public List<Room> findAll();
}
