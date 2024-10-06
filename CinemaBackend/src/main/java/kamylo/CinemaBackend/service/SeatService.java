package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.ShowTimeException;
import kamylo.CinemaBackend.model.Seat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatService {
    List<Seat> getAvailableSeatsByShowTimeId(Integer showTimeId) throws ShowTimeException;
}
