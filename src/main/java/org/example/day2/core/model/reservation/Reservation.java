package org.example.day2.core.model.reservation;

import lombok.Builder;
import org.example.day2.core.dto.booking.reservation.BookingRequest;

import java.time.LocalDateTime;

@Builder
public record Reservation(
        int id,
        int userId,
        int roomId,
        LocalDateTime checkIn,
        LocalDateTime checkOut
) {
    public static Reservation from(BookingRequest request) {
        return Reservation.builder()
                .userId(request.userId())
                .roomId(request.roomId())
                .checkIn(request.checkIn())
                .checkOut(request.checkOut())
                .build();
    }
}
