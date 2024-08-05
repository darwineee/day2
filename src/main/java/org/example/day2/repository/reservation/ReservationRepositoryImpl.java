package org.example.day2.repository.reservation;

import lombok.RequiredArgsConstructor;
import org.example.day2.core.model.reservation.Reservation;
import org.example.day2.core.model.room.Room;
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
        var sql = "select * from reservations";
        return jdbcClient.sql(sql)
                .query(Reservation.class)
                .list();
    }

    @Override
    public List<Reservation> findAllByUserId(int userId) {
        var sql = "select * from reservations where user_id = :userId";
        return jdbcClient.sql(sql)
                .param("userId", userId)
                .query(Reservation.class)
                .list();
    }

    @Override
    public void insertBooking(Reservation reservation) {
        var sql = """
                insert into reservations (user_id, room_id, check_in, check_out)
                values (:userId, :roomId, :checkIn, :checkOut)
               """.stripIndent();
        jdbcClient.sql(sql)
                .param("user_id", reservation.userId())
                .param("room_id", reservation.roomId())
                .param("check_in", reservation.checkIn())
                .param("check_out", reservation.checkOut())
                .update();
    }

    @Override
    public void cancelBooking(Reservation reservation) {

    }
}
