package org.example.day2.core.model.reservation;

import lombok.Builder;

@Builder
public record Reservation(
        int id,
        int userId,
        int roomId,
        long checkIn,
        long checkOut
) {
}
