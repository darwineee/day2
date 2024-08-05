package org.example.day2.core.service.booking;

import org.example.day2.core.dto.booking.reservation.*;
import org.example.day2.core.exception.booking.RoomUnavailable;

public interface BookingService {
    GetBookingListResponse getBookingList();
    GetUserBookingResponse getUserBooking(GetUserBookingRequest request);
    BookingResponse booking(BookingRequest request) throws RoomUnavailable;
}
