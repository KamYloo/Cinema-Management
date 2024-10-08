package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.SeatException;
import kamylo.CinemaBackend.exception.ShowTimeException;
import kamylo.CinemaBackend.model.Seat;
import kamylo.CinemaBackend.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImplementation implements SeatService{
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public List<Seat> getAvailableSeatsByShowTimeId(Integer showTimeId) throws ShowTimeException {
        return seatRepository.findByShowTimeId(showTimeId);
    }

    @Override
    public Seat getSeat(Integer seatId) throws SeatException {
       return seatRepository.findById(seatId).orElseThrow(() -> new SeatException("Seat not found with id: " + seatId));
    }

}
