package kamylo.CinemaBackend.service;

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
}
