package org.example.day2.service.booking;

import lombok.RequiredArgsConstructor;
import org.example.day2.core.dto.booking.reservation.*;
import org.example.day2.core.exception.booking.RoomUnavailable;
import org.example.day2.core.exception.user.UserNotFoundException;
import org.example.day2.core.model.reservation.Reservation;
import org.example.day2.core.repository.reservation.ReservationRepository;
import org.example.day2.core.repository.room.RoomRepository;
import org.example.day2.core.repository.user.UserRepository;
import org.example.day2.core.service.booking.BookingService;
import org.example.day2.core.utils.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public GetUserBookingResponse getUserBooking(GetUserBookingRequest request) throws UserNotFoundException {
        var user = userRepository.findByIdentity(request.email());
        if (user.isEmpty()) throw new UserNotFoundException();
        var data = reservationRepository.findAllByUserId(user.get().id());
        return GetUserBookingResponse.builder()
                .reservations(data)
                .build();
    }

    @Transactional(
            rollbackFor = RoomUnavailable.class,
            timeout = 300,
            isolation = Isolation.REPEATABLE_READ
    )
    @Override
    public BookingResponse booking(BookingRequest request) throws RoomUnavailable {
        var room = roomRepository.acquireRoomWithLock(request.roomId());
        if (room.isEmpty()) throw new RoomUnavailable(Message.ROOM_ID_INVALID);
        if (room.get().onBooking()) throw new RoomUnavailable(Message.ROOM_ON_BOOKING);
        roomRepository.setLock(request.roomId(), true);
        var isConflict = reservationRepository
                .findAllByUserId(request.userId())
                .stream()
                .anyMatch(reservation ->
                        inDateRange(request.checkIn(), reservation.checkIn(), reservation.checkOut())
                                || inDateRange(request.checkOut(), reservation.checkIn(), reservation.checkOut())
                );
        if (isConflict) throw new RoomUnavailable(Message.ROOM_UNAVAILABLE);
        reservationRepository.insertBooking(Reservation.from(request));
        var reservations = reservationRepository.findAllByUserId(request.userId());
        roomRepository.setLock(request.roomId(), false);
        return BookingResponse.builder().reservations(reservations).build();
    }

    private boolean inDateRange(LocalDateTime src, LocalDateTime start, LocalDateTime end) {
        return src.isAfter(start) && src.isBefore(end);
    }
}
