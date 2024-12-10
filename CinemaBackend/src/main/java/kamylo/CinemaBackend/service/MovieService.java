package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.MovieException;
import kamylo.CinemaBackend.exception.UserException;
import kamylo.CinemaBackend.model.Movie;
import kamylo.CinemaBackend.request.MovieRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Service
public interface MovieService {
    Movie createMovie(MovieRequest movieRequest) throws UserException;
    Set<Movie> getAllMovies();
    Movie getMovie(Integer movieId) throws MovieException;
    Set<Movie> searchMovieByTitle(String title);
    String addMoviePicture(MultipartFile file);
    void deleteMovie(Integer movieId, Integer userId) throws MovieException, UserException;
}
