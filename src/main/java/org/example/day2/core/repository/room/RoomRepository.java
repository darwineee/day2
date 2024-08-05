package org.example.day2.core.repository.room;

import org.example.day2.core.model.room.Room;

import java.util.List;

public interface RoomRepository {
    List<Room> findAll();
    Room findRoomById(int roomId);
    Room findRoomByIdWithLock(int roomId);
}
