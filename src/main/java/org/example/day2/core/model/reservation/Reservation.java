package org.example.day2.core.model.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.example.day2.core.dto.booking.reservation.BookingRequest;
import org.example.day2.core.utils.TimeP;

import java.time.LocalDateTime;

@Builder
public record Reservation(
        int id,
        int userId,
        int roomId,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeP.ISO8601_DATETIME)
        LocalDateTime checkIn,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeP.ISO8601_DATETIME)
        LocalDateTime checkOut
) {
    public static Reservation from(BookingRequest request, int userId) {
        return Reservation.builder()
                .userId(userId)
                .roomId(request.roomId())
                .checkIn(request.checkIn())
                .checkOut(request.checkOut())
                .build();
    }
}
