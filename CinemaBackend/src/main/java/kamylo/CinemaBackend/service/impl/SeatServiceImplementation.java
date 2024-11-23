package kamylo.CinemaBackend.service.impl;

import kamylo.CinemaBackend.exception.SeatException;
import kamylo.CinemaBackend.exception.ShowTimeException;
import kamylo.CinemaBackend.model.Seat;
import kamylo.CinemaBackend.repository.SeatRepository;
import kamylo.CinemaBackend.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImplementation implements SeatService {

    private final SeatRepository seatRepository;

    @Override
    public List<Seat> getAvailableSeatsByShowTimeId(Integer showTimeId) throws ShowTimeException {
        return seatRepository.findByShowTimeId(showTimeId);
    }

    @Override
    public Seat getSeat(Integer seatId) throws SeatException {
       return seatRepository.findById(seatId).orElseThrow(() -> new SeatException("Seat not found with id: " + seatId));
    }

}
