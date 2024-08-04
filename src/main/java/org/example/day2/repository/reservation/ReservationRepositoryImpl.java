package org.example.day2.repository.reservation;

import lombok.RequiredArgsConstructor;
import org.example.day2.core.model.reservation.Reservation;
import org.example.day2.core.repository.reservation.ReservationRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcClient jdbcClient;

    @Override
    public List<Reservation> findAll() {
        return List.of();
    }

    @Override
    public void book(Reservation reservation) {

    }

    @Override
    public void cancel(Reservation reservation) {

    }
}
