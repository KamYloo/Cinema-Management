package kamylo.CinemaBackend.controller;

import kamylo.CinemaBackend.dto.MovieDto;
import kamylo.CinemaBackend.dto.ShowTimeDto;
import kamylo.CinemaBackend.dto.mapper.MovieDtoMapper;
import kamylo.CinemaBackend.dto.mapper.ShowTimeDtoMapper;
import kamylo.CinemaBackend.exception.MovieException;
import kamylo.CinemaBackend.exception.ShowTimeException;
import kamylo.CinemaBackend.model.Movie;
import kamylo.CinemaBackend.model.ShowTime;
import kamylo.CinemaBackend.response.ApiResponse;
import kamylo.CinemaBackend.service.ShowTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/showTimes")
public class ShowTimeController {

    private final ShowTimeService showTimeService;

    @GetMapping("/{showTimeId}")
    public ResponseEntity<?> getShowTimeHandler(@PathVariable Integer showTimeId) {
        try {
            ShowTime showTime = showTimeService.getShowTime(showTimeId);
            ShowTimeDto showTimeDto = ShowTimeDtoMapper.toShowTimeDto(showTime);
            return new ResponseEntity<>(showTimeDto, HttpStatus.OK);
        } catch (ShowTimeException e) {
            ApiResponse response = new ApiResponse();
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowTimeDto>> getShowTimesForMovie(@PathVariable Integer movieId) throws MovieException {
        List<ShowTime> showTimes = showTimeService.getShowTimeByMovieId(movieId);
        List<ShowTimeDto> showTimeDtos = ShowTimeDtoMapper.toShowTimeDtos(showTimes);
        return new ResponseEntity<>(showTimeDtos, HttpStatus.OK);
    }
}
