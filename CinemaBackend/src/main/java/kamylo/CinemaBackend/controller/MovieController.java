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
    public ResponseEntity<?> createMovieHandler(@RequestBody MovieRequest movieRequest, @RequestHeader("Authorization") String token) {
        try {
            User user = userService.findUserProfileByJwt(token);
            if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
                throw new UserException("User is not ADMIN");
            }
            movieRequest.setUser(user);
            Movie movie = movieService.createMovie(movieRequest);
            MovieDto movieDto = MovieDtoMapper.toMovieDto(movie);
            return new ResponseEntity<>(movieDto, HttpStatus.CREATED);
        } catch (UserException e) {
            ApiResponse response = new ApiResponse();
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<?> getMovieHandler(@PathVariable Integer movieId) {
        try {
            Movie movie = movieService.getMovie(movieId);
            MovieDto movieDto = MovieDtoMapper.toMovieDto(movie);
            return new ResponseEntity<>(movieDto, HttpStatus.OK);
        } catch (MovieException e) {
            ApiResponse response = new ApiResponse();
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
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
        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            res.setMessage("User is not ADMIN");
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
        }
        try {
            movieService.deleteMovie(movieId, user.getId());
            res.setMessage("Movie deleted successfully.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (UserException | MovieException e) {
            res.setMessage(e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }
}
