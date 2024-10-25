package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.MovieException;
import kamylo.CinemaBackend.exception.ShowTimeException;
import kamylo.CinemaBackend.model.ShowTime;
import kamylo.CinemaBackend.repository.ShowTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShowTimeService {
    ShowTime getShowTime(Integer showTimeId) throws ShowTimeException;
    List<ShowTime> getShowTimeByMovieId(Integer movieId) throws MovieException;
}
