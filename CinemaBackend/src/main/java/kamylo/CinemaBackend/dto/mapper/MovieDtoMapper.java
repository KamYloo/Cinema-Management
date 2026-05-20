package kamylo.CinemaBackend.dto.mapper;

import kamylo.CinemaBackend.dto.MovieDto;
import kamylo.CinemaBackend.dto.UserDto;
import kamylo.CinemaBackend.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDtoMapper {
    public static MovieDto toMovieDto(Movie movie) {
        UserDto user = UserDtoMapper.toUserDto(movie.getUser());
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setDescription(movie.getDescription());
        movieDto.setImage(movie.getImage());
        movieDto.setDuration(movie.getDuration());
        movieDto.setGenre(movie.getGenre());
        movieDto.setUser(user);
        return movieDto;
    }

    public static List<MovieDto> toMovieDtoList(List<Movie> movies) {
        List<MovieDto> movieDtoList = new ArrayList<>();
        for (Movie movie : movies) {
            movieDtoList.add(toMovieDto(movie));
        }
        return movieDtoList;
    }
}