package org.example.day2.controller.v1.booking;

import lombok.RequiredArgsConstructor;
import org.example.day2.core.dto.booking.room.GetRoomsResponse;
import org.example.day2.core.service.booking.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<GetRoomsResponse> getRooms() {
        var rooms = roomService.getListRoom();
        return ResponseEntity.ok(rooms);
    }
}
