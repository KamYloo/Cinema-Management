package org.example.movie;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.dto.MovieDto;
import org.example.response.ApiResponse;
import org.example.utils.HttpUtils;
import org.example.utils.JwtUtils;

import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;

public class MovieService {
    private static final String MOVIE_ENDPOINT = "/api/movies";
    private static final Gson gson = new Gson();

    public static MovieDto createMovie(String jsonBody, String token) throws Exception {
        HttpResponse<String> response = HttpUtils.postWithToken(MOVIE_ENDPOINT + "/create", jsonBody, token);
        return gson.fromJson(response.body(), MovieDto.class);
    }

    public static MovieDto getMovie(int movieId) throws Exception {
        HttpResponse<String> response = HttpUtils.get(MOVIE_ENDPOINT + "/" + movieId, JwtUtils.getJwtToken());

        if (response.statusCode() == 200) {  // HTTP 200 oznacza sukces
            return gson.fromJson(response.body(), MovieDto.class);
        } else {
            throw new RuntimeException("Error fetching movie: " + response.body());
        }
    }

    public static Set<MovieDto> getAllMovies() throws Exception {
        HttpResponse<String> response = HttpUtils.get(MOVIE_ENDPOINT + "/getAll", JwtUtils.getJwtToken());

        if (response.statusCode() == 200) {
            Type movieSetType = new TypeToken<HashSet<MovieDto>>(){}.getType();
            return gson.fromJson(response.body(), movieSetType);
        } else {
            throw new RuntimeException("Error fetching all movies: " + response.body());
        }
    }

    public static ApiResponse deleteMovie(Integer movieId) throws Exception {
        HttpResponse<String> response = HttpUtils.delete(MOVIE_ENDPOINT + "/delete/" + movieId, JwtUtils.getJwtToken());

        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), ApiResponse.class);
        } else {
            throw new RuntimeException("Error deleting movie: " + response.body());
        }
    }

    public static Set<MovieDto> searchMovie(String title) throws Exception {
        HttpResponse<String> response = HttpUtils.get(MOVIE_ENDPOINT + "/search?title=" + title, JwtUtils.getJwtToken());

        if (response.statusCode() == 200) {
            Type movieSetType = new TypeToken<HashSet<MovieDto>>(){}.getType();
            return gson.fromJson(response.body(), movieSetType);
        } else {
            throw new RuntimeException("Error searching for movie: " + response.body());
        }
    }
}
