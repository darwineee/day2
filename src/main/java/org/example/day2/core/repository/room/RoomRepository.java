package org.example.day2.core.repository.room;

import org.example.day2.core.model.room.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    List<Room> findAll();
    Optional<Room> findById(int id);
}
