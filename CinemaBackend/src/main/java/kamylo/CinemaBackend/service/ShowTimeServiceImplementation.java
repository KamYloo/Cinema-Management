package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.MovieException;
import kamylo.CinemaBackend.exception.ShowTimeException;
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

    @Override
    public ShowTime getShowTime(Integer showTimeId) throws ShowTimeException {
        return showTimeRepository.findById(showTimeId).orElseThrow(() -> new ShowTimeException("Show time not found with id: " + showTimeId));
    }

    @Override
    public List<ShowTime> getShowTimeByMovieId(Integer movieId) throws MovieException {
        List<ShowTime> showTimes = showTimeRepository.findByMovieId(movieId);

        for (ShowTime showTime : showTimes) {
            if (showTime.getSeats() == null || showTime.getSeats().isEmpty()) {
                initializeSeatsForShowTime(showTime);
            }
        }
        return showTimes;
    }

    private void initializeSeatsForShowTime(ShowTime showTime) {
        List<Seat> seats = new ArrayList<>();

        int seatsPerRow = 6;
        int DEFAULT_SEAT_COUNT = 30;
        for (int i = 0; i < DEFAULT_SEAT_COUNT; i++) {
            Seat seat = new Seat();
            seat.setRowNumber(i / seatsPerRow + 1);
            seat.setSeatNumber(i % seatsPerRow + 1);
            seat.setReserved(false);
            seat.setShowTime(showTime);
            seats.add(seat);
        }

        seatRepository.saveAll(seats);
        showTime.setSeats(seats);
    }
}
