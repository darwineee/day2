package org.example.day2.core.dto.booking.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.example.day2.core.annotation.ValidationDateRange;
import org.example.day2.core.annotation.validate.DateRange;
import org.example.day2.core.utils.TimeP;

import java.time.LocalDateTime;

@DateRange(startDateField = "checkIn", endDateField = "checkOut")
public record BookingRequest(
        @Min(0)
        @Max(Integer.MAX_VALUE)
        int roomId,

        @FutureOrPresent
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeP.ISO8601_DATETIME)
        LocalDateTime checkIn,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeP.ISO8601_DATETIME)
        LocalDateTime checkOut

) implements ValidationDateRange {
    @Override
    public LocalDateTime getStartDate() {
        return checkIn;
    }

    @Override
    public LocalDateTime getEndDate() {
        return checkOut;
    }
}
