package org.example.day2.core.service.booking;

import org.example.day2.core.dto.booking.reservation.*;
import org.example.day2.core.exception.booking.RoomUnavailable;
import org.example.day2.core.exception.user.UserNotFoundException;

public interface BookingService {
    GetUserBookingResponse getUserBooking(GetUserBookingRequest request) throws UserNotFoundException;
    BookingResponse booking(BookingRequest request) throws RoomUnavailable;
}
