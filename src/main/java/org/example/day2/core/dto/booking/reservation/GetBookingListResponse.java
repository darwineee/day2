package org.example.day2.core.dto.booking.reservation;

import lombok.Builder;
import org.example.day2.core.model.reservation.PublicReservation;

import java.util.List;

@Builder
public record GetBookingListResponse(
        List<PublicReservation> reservations
) {
}
