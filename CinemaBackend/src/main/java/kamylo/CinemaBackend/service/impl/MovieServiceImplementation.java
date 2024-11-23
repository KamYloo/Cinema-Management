package kamylo.CinemaBackend.service.impl;

import kamylo.CinemaBackend.exception.MovieException;
import kamylo.CinemaBackend.exception.UserException;
import kamylo.CinemaBackend.model.Movie;
import kamylo.CinemaBackend.model.ShowTime;
import kamylo.CinemaBackend.model.User;
import kamylo.CinemaBackend.repository.MovieRepository;
import kamylo.CinemaBackend.repository.ShowTimeRepository;
import kamylo.CinemaBackend.request.MovieRequest;
import kamylo.CinemaBackend.service.MovieService;
import kamylo.CinemaBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovieServiceImplementation implements MovieService {

    private final MovieRepository movieRepository;
    private final UserService userService;
    private final ShowTimeRepository showTimeRepository;

    @Override
    public Movie createMovie(MovieRequest movieRequest) throws UserException {
        User user = userService.findUserById(movieRequest.getUser().getId());
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            Movie movie = new Movie();
            movie.setTitle(movieRequest.getTitle());
            movie.setDescription(movieRequest.getDescription());
            movie.setDuration(movieRequest.getDuration());
            movie.setGenre(movieRequest.getGenre());
            movie.setImage(movieRequest.getImage());
            movie.setUser(user);
            Movie savedMovie = movieRepository.save(movie);
            List<ShowTime> showTimes = new ArrayList<>();
            for (ShowTime showTimeRequest : movieRequest.getShowTimes()) {
                ShowTime showTime = new ShowTime();
                showTime.setTime(showTimeRequest.getTime());
                showTime.setMovie(savedMovie);
                showTimes.add(showTime);
            }
            showTimeRepository.saveAll(showTimes);
            return savedMovie;
        }
        else {
            throw new UserException("User is not ADMIN");
        }
    }

    @Override
    public Set<Movie> getAllMovies() {
        return movieRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Movie getMovie(Integer movieId) throws MovieException {
        return movieRepository.findById(movieId).orElseThrow(() -> new MovieException("Movie not found with id: " + movieId));
    }

    @Override
    public Set<Movie> searchMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Override
    public void deleteMovie(Integer movieId, Integer userId) throws MovieException, UserException {
        Movie movie = getMovie(movieId);
        if (movie == null) {
            throw new MovieException("Movie not found with id: " + movieId);
        }
        if (!userId.equals(movie.getUser().getId())) {
            throw new UserException("User is not ADMIN");
        }
        movieRepository.deleteById(movieId);
    }
}