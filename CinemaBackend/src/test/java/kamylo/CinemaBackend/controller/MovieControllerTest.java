package kamylo.CinemaBackend.controller;

import kamylo.CinemaBackend.dto.MovieDto;
import kamylo.CinemaBackend.dto.mapper.MovieDtoMapper;
import kamylo.CinemaBackend.exception.MovieException;
import kamylo.CinemaBackend.model.Movie;
import kamylo.CinemaBackend.model.User;
import kamylo.CinemaBackend.request.MovieRequest;
import kamylo.CinemaBackend.service.MovieService;
import kamylo.CinemaBackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Import(TestSecurityConfig.class)
@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private UserService userService;

    private User adminUser;
    private User regularUser;
    private Movie movie;
    private MovieDto movieDto;

    @BeforeEach
    void setUp() {
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

        movieDto = MovieDtoMapper.toMovieDto(movie);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateMovieHandler() throws Exception {
        when(userService.findUserProfileByJwt(anyString())).thenReturn(adminUser);
        when(movieService.createMovie(any(MovieRequest.class))).thenReturn(movie);

        mockMvc.perform(post("/api/movies/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer valid_token")
                        .content("{\"title\":\"Test Movie\", \"description\":\"Test Description\", \"duration\":120, \"genre\":\"Action\", \"image\":\"test_image.png\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Test Movie")))
                .andExpect(jsonPath("$.description", is("Test Description")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testCreateMovieHandlerWithNonAdminUser() throws Exception {
        when(userService.findUserProfileByJwt(anyString())).thenReturn(regularUser);

        mockMvc.perform(post("/api/movies/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer valid_token")
                        .content("{\"title\":\"Test Movie\", \"description\":\"Test Description\", \"duration\":120, \"genre\":\"Action\", \"image\":\"test_image.png\"}"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message", is("User is not ADMIN")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetMovieHandler() throws Exception {
        when(userService.findUserProfileByJwt(anyString())).thenReturn(adminUser);
        when(movieService.getMovie(1)).thenReturn(movie);

        mockMvc.perform(get("/api/movies/1")
                        .header("Authorization", "Bearer valid_token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Movie")))
                .andExpect(jsonPath("$.description", is("Test Description")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetMovieHandlerThrowsException() throws Exception {
        when(userService.findUserProfileByJwt(anyString())).thenReturn(adminUser);
        when(movieService.getMovie(99)).thenThrow(new MovieException("Movie not found with id: 99"));

        mockMvc.perform(get("/api/movies/99")
                        .header("Authorization", "Bearer valid_token"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Movie not found with id: 99")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllMoviesHandler() throws Exception {
        Set<Movie> movies = new HashSet<>();
        movies.add(movie);

        when(userService.findUserProfileByJwt(anyString())).thenReturn(adminUser);
        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/api/movies/getAll")
                        .header("Authorization", "Bearer valid_token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Test Movie")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testSearchMovieHandler() throws Exception {
        Set<Movie> movies = new HashSet<>();
        movies.add(movie);

        when(userService.findUserProfileByJwt(anyString())).thenReturn(adminUser);
        when(movieService.searchMovieByTitle("Test Movie")).thenReturn(movies);

        mockMvc.perform(get("/api/movies/search")
                        .param("title", "Test Movie")
                        .header("Authorization", "Bearer valid_token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Test Movie")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteMovieHandler() throws Exception {
        when(userService.findUserProfileByJwt(anyString())).thenReturn(adminUser);

        mockMvc.perform(delete("/api/movies/delete/1")
                        .header("Authorization", "Bearer valid_token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Movie deleted successfully.")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testDeleteMovieHandlerWithNonAdminUser() throws Exception {
        when(userService.findUserProfileByJwt(anyString())).thenReturn(regularUser);

        mockMvc.perform(delete("/api/movies/delete/1")
                        .header("Authorization", "Bearer valid_token"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message", is("User is not ADMIN")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteMovieHandlerThrowsException() throws Exception {
        when(userService.findUserProfileByJwt(anyString())).thenReturn(adminUser);
        doThrow(new MovieException("Movie not found with id: 99")).when(movieService).deleteMovie(99, adminUser.getId());

        mockMvc.perform(delete("/api/movies/delete/99")
                        .header("Authorization", "Bearer valid_token"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Movie not found with id: 99")));
    }
}
