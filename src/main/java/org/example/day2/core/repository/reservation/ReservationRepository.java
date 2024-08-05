package org.example.day2.core.repository.reservation;

import org.example.day2.core.model.reservation.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();
    List<Reservation> findAllByUserId(int userId);
    void insertBooking(Reservation reservation);
    void cancelBooking(Reservation reservation);
}
