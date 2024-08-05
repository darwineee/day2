package org.example.day2.core.model.room;

import lombok.Builder;

@Builder
public record Room(
        int id,
        char roomClass,
        boolean onBooking
) {
}
