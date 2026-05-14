package kamylo.CinemaBackend.service.impl;

import kamylo.CinemaBackend.exception.ReservationException;
import kamylo.CinemaBackend.exception.SeatException;
import kamylo.CinemaBackend.exception.UserException;
import kamylo.CinemaBackend.model.Reservation;
import kamylo.CinemaBackend.model.Seat;
import kamylo.CinemaBackend.model.User;
import kamylo.CinemaBackend.repository.ReservationRepository;
import kamylo.CinemaBackend.repository.SeatRepository;
import kamylo.CinemaBackend.service.ReservationService;
import kamylo.CinemaBackend.service.SeatAvailabilityPublisher;
import kamylo.CinemaBackend.service.SeatService;
import kamylo.CinemaBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImplementation implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final SeatService seatService;
    private final UserService userService;
    private final SeatRepository seatRepository;
    private final SeatAvailabilityPublisher seatAvailabilityPublisher;

    @Override
    @Transactional
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
            seat.setReserved(true);
            seatRepository.save(seat);
            Reservation savedReservation = reservationRepository.save(reservation);
            seatAvailabilityPublisher.publishSeatUpdate(seat, savedReservation, "RESERVED");
            return savedReservation;
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
    @Transactional
    public void deleteReservationById(Integer reservationId, Integer userId) throws ReservationException ,SeatException, UserException {
        Reservation reservation = getReservationById(reservationId);
        if (reservation == null) {
            throw new ReservationException("Reservation not found with id " + reservationId);
        }
        if (!userId.equals(reservation.getUser().getId())) {
            throw new UserException("User not authorized to delete reservation");
        }
        Seat seat = seatService.getSeat(reservation.getSeat().getId());
        seat.setReserved(false);
        seatRepository.save(seat);
        reservationRepository.deleteById(reservationId);
        seatAvailabilityPublisher.publishSeatUpdate(seat, reservation, "RELEASED");
    }
}
