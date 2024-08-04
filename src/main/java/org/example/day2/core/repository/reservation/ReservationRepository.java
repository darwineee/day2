package org.example.day2.core.repository.reservation;

import org.example.day2.core.exception.room.RoomUnavailable;
import org.example.day2.core.model.reservation.Reservation;

import java.util.List;

public interface ReservationRepository {
    public List<Reservation> findAll();
    public void book(Reservation reservation) throws RoomUnavailable;
    public void cancel(Reservation reservation);
}
