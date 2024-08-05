package org.example.day2.controller.v1.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.day2.component.JwtHelper;
import org.example.day2.core.dto.booking.reservation.*;
import org.example.day2.core.dto.wrapper.ErrorRsp;
import org.example.day2.core.exception.booking.RoomUnavailable;
import org.example.day2.core.exception.user.UserNotFoundException;
import org.example.day2.core.service.booking.BookingService;
import org.example.day2.core.utils.ErrCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final JwtHelper jwtHelper;

    @GetMapping("/booking")
    public ResponseEntity<GetUserBookingResponse> getUserBooking(
            @RequestHeader("Authorization") String authHeader
    ) throws UserNotFoundException {
        var token = authHeader.substring(7);
        var email = jwtHelper.extractEmail(token);
        var request = new GetUserBookingRequest(email);
        var response = bookingService.getUserBooking(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/booking")
    public ResponseEntity<BookingResponse> addBooking(
            @Valid
            @RequestBody
            BookingRequest bookingRequest
    ) throws RoomUnavailable {
        var response = bookingService.booking(bookingRequest);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ErrorRsp<String>> handle(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorRsp<>(ErrCode.USER_NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(RoomUnavailable.class)
    private ResponseEntity<ErrorRsp<String>> handle(RoomUnavailable ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorRsp<>(ErrCode.ROOM_UNAVAILABLE, ex.getMessage()));
    }
}
