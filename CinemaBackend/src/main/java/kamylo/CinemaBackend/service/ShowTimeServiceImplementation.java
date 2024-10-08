package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.MovieException;
import kamylo.CinemaBackend.model.Seat;
import kamylo.CinemaBackend.model.ShowTime;
import kamylo.CinemaBackend.repository.SeatRepository;
import kamylo.CinemaBackend.repository.ShowTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowTimeServiceImplementation implements ShowTimeService {
    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private SeatRepository seatRepository;

    private final int DEFAULT_SEAT_COUNT = 30;

    @Override
    public List<ShowTime> getShowTimeByMovieId(Integer movieId) throws MovieException {
        List<ShowTime> showTimes = showTimeRepository.findByMovieId(movieId);

        for (ShowTime showTime : showTimes) {
            if (showTime.getSeats() == null || showTime.getSeats().isEmpty()) {
                initializeSeatsForShowTime(showTime, DEFAULT_SEAT_COUNT);
            }
        }
        return showTimes;
    }

    private void initializeSeatsForShowTime(ShowTime showTime, int seatCount) {
        List<Seat> seats = new ArrayList<>();

        for (int i = 1; i <= seatCount; i++) {
            Seat seat = new Seat();
            seat.setRowNumber((i - 1) / 10 + 1);
            seat.setSeatNumber(i);
            seat.setReserved(false);
            seat.setShowTime(showTime);
            seats.add(seat);
        }

        seatRepository.saveAll(seats);
    }
}
