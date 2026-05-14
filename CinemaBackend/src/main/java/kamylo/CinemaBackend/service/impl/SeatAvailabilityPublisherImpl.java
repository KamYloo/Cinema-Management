package kamylo.CinemaBackend.service.impl;

import kamylo.CinemaBackend.dto.SeatAvailabilityUpdateDto;
import kamylo.CinemaBackend.model.Reservation;
import kamylo.CinemaBackend.model.Seat;
import kamylo.CinemaBackend.service.SeatAvailabilityPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatAvailabilityPublisherImpl implements SeatAvailabilityPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void publishSeatUpdate(Seat seat, Reservation reservation, String action) {
        messagingTemplate.convertAndSend(
                "/topic/showtimes/" + seat.getShowTime().getId() + "/seats",
                createPayload(seat, reservation, action)
        );
    }

    @Override
    public void publishSeatUpdate(Seat seat, String action) {
        publishSeatUpdate(seat, null, action);
    }

    @Override
    public SeatAvailabilityUpdateDto createPayload(Seat seat, Reservation reservation, String action) {
        return new SeatAvailabilityUpdateDto(
                seat.getShowTime().getId(),
                seat.getId(),
                seat.getSeatNumber(),
                seat.getRowNumber(),
                seat.isReserved(),
                action,
                reservation != null ? reservation.getId() : null
        );
    }
}