package org.example.day2.core.model.reservation;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PublicReservation(
        int id,
        int roomId,
        LocalDateTime checkIn,
        LocalDateTime checkOut
) {
    public static PublicReservation from(Reservation reservation) {
        return PublicReservation.builder()
                .id(reservation.id())
                .roomId(reservation.roomId())
                .checkIn(reservation.checkIn())
                .checkOut(reservation.checkOut())
                .build();
    }
}
