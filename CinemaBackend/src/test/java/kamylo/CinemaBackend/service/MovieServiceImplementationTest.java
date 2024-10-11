package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.MovieException;
import kamylo.CinemaBackend.exception.UserException;
import kamylo.CinemaBackend.model.Movie;
import kamylo.CinemaBackend.model.ShowTime;
import kamylo.CinemaBackend.model.User;
import kamylo.CinemaBackend.repository.MovieRepository;
import kamylo.CinemaBackend.repository.ShowTimeRepository;
import kamylo.CinemaBackend.request.MovieRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplementationTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserService userService;

    @Mock
    private ShowTimeRepository showTimeRepository;

    @InjectMocks
    private MovieServiceImplementation movieService;

    private MovieRequest movieRequest;
    private User adminUser;
    private User regularUser;
    private Movie movie;
    private ShowTime showTime;
    private List<ShowTime> showTimes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        adminUser = new User();
        adminUser.setId(1);
        adminUser.setRole("ADMIN");

        regularUser = new User();
        regularUser.setId(2);
        regularUser.setRole("USER");

        movie = new Movie();
        movie.setId(1);
        movie.setTitle("Test Movie");
        movie.setDescription("Test Description");
        movie.setDuration(120);
        movie.setGenre("Action");
        movie.setImage("test_image.png");
        movie.setUser(adminUser);

        showTime = new ShowTime();
        showTime.setId(1);
        showTime.setTime(LocalDateTime.now());
        showTime.setMovie(movie);

        showTimes = new ArrayList<>();
        showTimes.add(showTime);

        movieRequest = new MovieRequest();
        movieRequest.setTitle("Test Movie");
        movieRequest.setDescription("Test Description");
        movieRequest.setDuration(120);
        movieRequest.setGenre("Action");
        movieRequest.setImage("test_image.png");
        movieRequest.setUser(adminUser);
        movieRequest.setShowTimes(showTimes);
    }

    @Test
    void testCreateMovieWithAdminUser() throws UserException {
        when(userService.findUserById(adminUser.getId())).thenReturn(adminUser);
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie createdMovie = movieService.createMovie(movieRequest);

        assertNotNull(createdMovie);
        assertEquals("Test Movie", createdMovie.getTitle());
        verify(movieRepository, times(1)).save(any(Movie.class));
        verify(showTimeRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testCreateMovieWithNonAdminUserThrowsException() throws UserException {
        movieRequest.setUser(regularUser);
        when(userService.findUserById(regularUser.getId())).thenReturn(regularUser);

        UserException exception = assertThrows(UserException.class, () -> {
            movieService.createMovie(movieRequest);
        });

        assertEquals("User is not ADMIN", exception.getMessage());
        verify(movieRepository, never()).save(any(Movie.class));
        verify(showTimeRepository, never()).saveAll(anyList());
    }

    @Test
    void testGetAllMovies() {
        Set<Movie> movies = new LinkedHashSet<>();
        movies.add(movie);
        when(movieRepository.findAllByOrderByIdDesc()).thenReturn(movies);

        Set<Movie> fetchedMovies = movieService.getAllMovies();

        assertEquals(1, fetchedMovies.size());
        verify(movieRepository, times(1)).findAllByOrderByIdDesc();
    }

    @Test
    void testGetMovieById() throws MovieException {
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));

        Movie fetchedMovie = movieService.getMovie(movie.getId());

        assertNotNull(fetchedMovie);
        assertEquals("Test Movie", fetchedMovie.getTitle());
        verify(movieRepository, times(1)).findById(movie.getId());
    }

    @Test
    void testGetMovieByIdThrowsException() {
        when(movieRepository.findById(99)).thenReturn(Optional.empty());

        MovieException exception = assertThrows(MovieException.class, () -> {
            movieService.getMovie(99);
        });

        assertEquals("Movie not found with id: 99", exception.getMessage());
    }

    @Test
    void testSearchMovieByTitle() {
        Set<Movie> movies = new HashSet<>();
        movies.add(movie);
        when(movieRepository.findByTitle("Test Movie")).thenReturn(movies);

        Set<Movie> foundMovies = movieService.searchMovieByTitle("Test Movie");

        assertEquals(1, foundMovies.size());
        verify(movieRepository, times(1)).findByTitle("Test Movie");
    }

    @Test
    void testDeleteMovieWithAdminUser() throws MovieException, UserException {
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));

        movieService.deleteMovie(movie.getId(), adminUser.getId());

        verify(movieRepository, times(1)).deleteById(movie.getId());
    }

    @Test
    void testDeleteMovieWithNonAdminUserThrowsException() {
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));

        UserException exception = assertThrows(UserException.class, () -> {
            movieService.deleteMovie(movie.getId(), regularUser.getId());
        });

        assertEquals("User is not ADMIN", exception.getMessage());
        verify(movieRepository, never()).deleteById(movie.getId());
    }

    @Test
    void testDeleteMovieNotFoundThrowsException() {
        when(movieRepository.findById(99)).thenReturn(Optional.empty());

        MovieException exception = assertThrows(MovieException.class, () -> {
            movieService.deleteMovie(99, adminUser.getId());
        });

        assertEquals("Movie not found with id: 99", exception.getMessage());
    }
}
