package org.example.day2.core.dto.booking.room;

import lombok.Builder;
import org.example.day2.core.model.room.Room;

import java.util.List;

@Builder
public record GetRoomsResponse(
        List<Room> rooms
) {
}
