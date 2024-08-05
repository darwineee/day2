package org.example.day2.service.booking;

import lombok.RequiredArgsConstructor;
import org.example.day2.core.dto.booking.reservation.*;
import org.example.day2.core.exception.booking.RoomUnavailable;
import org.example.day2.core.model.reservation.PublicReservation;
import org.example.day2.core.model.reservation.Reservation;
import org.example.day2.core.repository.reservation.ReservationRepository;
import org.example.day2.core.repository.room.RoomRepository;
import org.example.day2.core.service.booking.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Override
    public GetBookingListResponse getBookingList() {
        var data = reservationRepository
                .findAll()
                .stream()
                .map(PublicReservation::from)
                .toList();
        return GetBookingListResponse.builder()
                .reservations(data)
                .build();
    }

    @Override
    public GetUserBookingResponse getUserBooking(GetUserBookingRequest request) {
        var data = reservationRepository.findAllByUserId(request.userId());
        return GetUserBookingResponse.builder()
                .reservations(data)
                .build();
    }

    @Transactional
    @Override
    public BookingResponse booking(BookingRequest request) throws RoomUnavailable {
        var room = roomRepository.findRoomByIdWithLock(request.roomId());
        if (room == null) throw new RoomUnavailable();
        var isConflict = reservationRepository
                .findAllByUserId(request.userId())
                .stream()
                .anyMatch(reservation ->
                        inDateRange(request.checkIn(), reservation.checkIn(), reservation.checkOut())
                                || inDateRange(request.checkOut(), reservation.checkIn(), reservation.checkOut())
                );
        if (isConflict) throw new RoomUnavailable();
        reservationRepository.insertBooking(Reservation.from(request));
        var reservations = reservationRepository.findAllByUserId(request.userId());
        return new BookingResponse(reservations);
    }

    private boolean inDateRange(LocalDateTime src, LocalDateTime start, LocalDateTime end) {
        return src.isAfter(start) && src.isBefore(end);
    }
}
