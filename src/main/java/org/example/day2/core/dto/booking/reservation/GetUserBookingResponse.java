package org.example.day2.core.dto.booking.reservation;

import lombok.Builder;
import org.example.day2.core.model.reservation.Reservation;

import java.util.List;

@Builder
public record GetUserBookingResponse(
        List<Reservation> reservations
) {
}
