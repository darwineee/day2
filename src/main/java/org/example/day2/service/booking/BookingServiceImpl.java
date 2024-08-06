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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    private static final int ROOM_BOOKING_PERIOD = 3; //minutes

    @Override
    public GetUserBookingResponse getUserBooking(String userEmail) throws UserNotFoundException {
        var user = userRepository.findByIdentity(userEmail);
        if (user.isEmpty()) throw new UserNotFoundException();
        var data = reservationRepository.findAllByUserId(user.get().id());
        return GetUserBookingResponse.builder()
                .reservations(data)
                .build();
    }

    @Transactional(
            rollbackFor = RoomUnavailable.class,
            isolation = Isolation.REPEATABLE_READ
    )
    @Override
    public BookingResponse booking(BookingRequest request, String userEmail) throws RoomUnavailable, UserNotFoundException {
        var userId = userRepository.findByIdentity(userEmail)
                .orElseThrow(UserNotFoundException::new)
                .id();
        roomRepository
                .findById(request.roomId())
                .orElseThrow(() -> new RoomUnavailable(Message.ROOM_ID_INVALID));
        var key = "lock:room-booking:" + request.roomId();
        var value = UUID.randomUUID().toString();
        var acquiredLock = redisTemplate
                .opsForValue()
                .setIfAbsent(key, value, ROOM_BOOKING_PERIOD, TimeUnit.MINUTES);
        if (Boolean.FALSE.equals(acquiredLock)) throw new RoomUnavailable(Message.ROOM_ON_BOOKING);
        var isPeriodConflict = reservationRepository
                .findAllByUserId(userId)
                .stream()
                .anyMatch(reservation ->
                        inDateRange(request.checkIn(), reservation.checkIn(), reservation.checkOut())
                                || inDateRange(request.checkOut(), reservation.checkIn(), reservation.checkOut())
                );
        if (isPeriodConflict) throw new RoomUnavailable(Message.ROOM_UNAVAILABLE);
        reservationRepository.insertBooking(Reservation.from(request, userId));
        var reservations = reservationRepository.findAllByUserId(userId);
        redisTemplate.delete(key);
        return BookingResponse.builder().reservations(reservations).build();
    }

    private boolean inDateRange(LocalDateTime src, LocalDateTime start, LocalDateTime end) {
        return src.isEqual(start) || src.isEqual(end) || (src.isAfter(start) && src.isBefore(end));
    }
}
