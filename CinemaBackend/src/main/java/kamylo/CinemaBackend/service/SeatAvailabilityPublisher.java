package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.dto.SeatAvailabilityUpdateDto;
import kamylo.CinemaBackend.model.Reservation;
import kamylo.CinemaBackend.model.Seat;

public interface SeatAvailabilityPublisher {
    void publishSeatUpdate(Seat seat, Reservation reservation, String action);

    void publishSeatUpdate(Seat seat, String action);

    SeatAvailabilityUpdateDto createPayload(Seat seat, Reservation reservation, String action);
}