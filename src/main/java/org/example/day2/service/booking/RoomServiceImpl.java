package org.example.day2.service.booking;

import lombok.RequiredArgsConstructor;
import org.example.day2.core.dto.booking.room.GetRoomsResponse;
import org.example.day2.core.repository.room.RoomRepository;
import org.example.day2.core.service.booking.RoomService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public GetRoomsResponse getListRoom() {
        return GetRoomsResponse.builder()
                .rooms(roomRepository.findAll())
                .build();
    }
}
