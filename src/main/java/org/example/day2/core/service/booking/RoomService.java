package org.example.day2.core.service.booking;

import org.example.day2.core.dto.booking.room.GetRoomsResponse;
import org.example.day2.core.model.room.Room;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    public GetRoomsResponse getListRoom();
}
