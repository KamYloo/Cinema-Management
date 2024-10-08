package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.ReservationException;
import kamylo.CinemaBackend.exception.SeatException;
import kamylo.CinemaBackend.exception.UserException;
import kamylo.CinemaBackend.model.Reservation;
import kamylo.CinemaBackend.model.Seat;
import kamylo.CinemaBackend.model.User;
import kamylo.CinemaBackend.repository.ReservationRepository;
import kamylo.CinemaBackend.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImplementation implements ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    SeatService seatService;

    @Autowired
    UserService userService;

    @Override
    public Reservation createReservation(Integer seatId, User user) throws SeatException {
        Seat seat = seatService.getSeat(seatId);
        if (seat.isReserved()) {
            throw new SeatException("Seat already reserved");
        }
        else {
            Reservation reservation = new Reservation();
            reservation.setSeat(seat);
            reservation.setUser(user);
            reservation.setShowtime(seat.getShowTime());
            return reservationRepository.save(reservation);
        }
    }

    @Override
    public List<Reservation> getReservationsByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        List<Reservation> reservations = user.getReservations();
        return (reservations != null) ? reservations : new ArrayList<>();
    }

    @Override
    public Reservation getReservationById(Integer reservationId) throws ReservationException {
      return reservationRepository.findById(reservationId).orElseThrow(() -> new ReservationException("Reservation not found with id " + reservationId));
    }

    @Override
    public void deleteReservationById(Integer reservationId, Integer userId) throws ReservationException, UserException {
        Reservation reservation = getReservationById(reservationId);
        if (reservation == null) {
            throw new ReservationException("Reservation not found with id " + reservationId);
        }
        if (!userId.equals(reservation.getUser().getId())) {
            throw new UserException("User not authorized to delete reservation");
        }
        reservationRepository.deleteById(reservationId);
    }
}
