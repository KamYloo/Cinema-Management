package kamylo.CinemaBackend.dto.mapper;

import kamylo.CinemaBackend.dto.MovieDto;
import kamylo.CinemaBackend.dto.UserDto;
import kamylo.CinemaBackend.model.Movie;

import java.util.HashSet;
import java.util.Set;

public class MovieDtoMapper {
    public static MovieDto toMovieDto(Movie movie) {
        UserDto user = UserDtoMapper.toUserDto(movie.getUser());
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setDescription(movie.getDescription());
        movieDto.setImage(movie.getImage());
        movieDto.setDuration(movie.getDuration());
        movieDto.setShowTimes(movie.getShowTimes());
        movieDto.setGenre(movie.getGenre());
        movieDto.setUser(user);
        return movieDto;
    }
    public static Set<MovieDto> toMovieDtoSet(Set<Movie> movies) {
        Set<MovieDto> movieDtoSet = new HashSet<>();
        for (Movie movie : movies) {
            movieDtoSet.add(toMovieDto(movie));
        }
        return movieDtoSet;
    }
}
