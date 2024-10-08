package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.ReservationException;
import kamylo.CinemaBackend.exception.SeatException;
import kamylo.CinemaBackend.exception.UserException;
import kamylo.CinemaBackend.model.Reservation;
import kamylo.CinemaBackend.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationService {
    Reservation createReservation(Integer SeatId, User user) throws SeatException;
    List<Reservation> getReservationsByUserId(Integer userId) throws UserException;
    Reservation getReservationById(Integer reservationId) throws ReservationException;
    void deleteReservationById(Integer reservationId, Integer userId) throws ReservationException, UserException;
}
