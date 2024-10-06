package kamylo.CinemaBackend.dto.mapper;

import kamylo.CinemaBackend.dto.MovieDto;
import kamylo.CinemaBackend.dto.ShowTimeDto;
import kamylo.CinemaBackend.model.Movie;
import kamylo.CinemaBackend.model.ShowTime;

import java.util.ArrayList;
import java.util.List;

public class ShowTimeDtoMapper {
    public static ShowTimeDto toShowTimeDto (ShowTime showTime) {
        MovieDto movie = MovieDtoMapper.toMovieDto(showTime.getMovie());
        ShowTimeDto showTimeDto = new ShowTimeDto();
        showTimeDto.setId(showTime.getId());
        showTimeDto.setTime(showTime.getTime());
        showTimeDto.setMovie(movie);
        return showTimeDto;
    }

    public static List<ShowTimeDto> toShowTimeDtos(List<ShowTime> showTimes) {
        List<ShowTimeDto> showTimeDtos = new ArrayList<>();
        for (ShowTime showTime : showTimes) {
            showTimeDtos.add(toShowTimeDto(showTime));
        }
        return showTimeDtos;
    }
}
