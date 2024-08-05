package org.example.day2.core.dto.booking.reservation;

import jakarta.validation.constraints.*;
import org.example.day2.core.annotation.ValidationDateRange;
import org.example.day2.core.annotation.validate.DateRange;

import java.time.LocalDateTime;

@DateRange(startDateField = "checkIn", endDateField = "checkOut")
public record BookingRequest(
        @Min(0)
        @Max(Integer.MAX_VALUE)
        int userId,

        @Min(0)
        @Max(Integer.MAX_VALUE)
        int roomId,

        @FutureOrPresent
        LocalDateTime checkIn,
        @Future
        LocalDateTime checkOut
) implements ValidationDateRange {
        @Override
        public LocalDateTime getStartDate() { return checkIn; }

        @Override
        public LocalDateTime getEndDate() { return checkOut; }
}
