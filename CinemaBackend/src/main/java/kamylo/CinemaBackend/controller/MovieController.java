package kamylo.CinemaBackend.controller;


import kamylo.CinemaBackend.dto.MovieDto;
import kamylo.CinemaBackend.dto.mapper.MovieDtoMapper;
import kamylo.CinemaBackend.exception.MovieException;
import kamylo.CinemaBackend.exception.UserException;
import kamylo.CinemaBackend.model.Movie;
import kamylo.CinemaBackend.model.User;
import kamylo.CinemaBackend.request.MovieRequest;
import kamylo.CinemaBackend.response.ApiResponse;
import kamylo.CinemaBackend.service.MovieService;
import kamylo.CinemaBackend.service.UserService;
import kamylo.CinemaBackend.util.MovieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<MovieDto> createMovieHandler(@RequestBody MovieRequest movieRequest, @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfileByJwt(token);
        movieRequest.setUser(user);
        Movie movie = movieService.createMovie(movieRequest);
        MovieDto movieDto = MovieDtoMapper.toMovieDto(movie);
        return new ResponseEntity<>(movieDto, HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId) throws MovieException {
        Movie movie = movieService.getMovie(movieId);
        MovieDto movieDto = MovieDtoMapper.toMovieDto(movie);
        return new ResponseEntity<>(movieDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Set<MovieDto>> getAllAlbumsHandler() {
        Set<Movie> movies = movieService.getAllMovies();
        Set<MovieDto> movieDtos = MovieDtoMapper.toMovieDtoSet(movies);
        return new ResponseEntity<>(movieDtos, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Set<MovieDto>> searchMovieHandler(@RequestParam("title") String title) {
        Set<Movie> movies = movieService.searchMovieByTitle(title);
        Set<MovieDto> movieDtos = MovieDtoMapper.toMovieDtoSet(movies);
        return new ResponseEntity<>(movieDtos, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<ApiResponse> deleteMovieHandler(@PathVariable Integer movieId , @RequestHeader("Authorization") String token) throws UserException{
        User user = userService.findUserProfileByJwt(token);
        ApiResponse res = new ApiResponse();

        try {
            movieService.deleteMovie(movieId, user.getId());
            res.setMessage("Movie deleted successfully.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (UserException | MovieException e) {
            res.setMessage(e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
        }
    }
}
