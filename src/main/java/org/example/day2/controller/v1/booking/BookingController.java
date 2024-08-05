package org.example.day2.controller.v1.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.day2.core.dto.booking.reservation.GetBookingListResponse;
import org.example.day2.core.dto.booking.reservation.GetUserBookingRequest;
import org.example.day2.core.dto.booking.reservation.GetUserBookingResponse;
import org.example.day2.core.service.booking.BookingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/booking")
    public GetBookingListResponse getBookingList() {
        return bookingService.getBookingList();
    }

    @GetMapping("/booking/{userId}")
    public GetUserBookingResponse getUserBooking(
            @PathVariable int userId
    ) {
        var request = new GetUserBookingRequest(userId);
        return bookingService.getUserBooking(request);
    }
}
