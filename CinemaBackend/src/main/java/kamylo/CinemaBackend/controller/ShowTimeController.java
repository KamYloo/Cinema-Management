package kamylo.CinemaBackend.controller;

import kamylo.CinemaBackend.dto.ShowTimeDto;
import kamylo.CinemaBackend.dto.mapper.ShowTimeDtoMapper;
import kamylo.CinemaBackend.exception.MovieException;
import kamylo.CinemaBackend.model.ShowTime;
import kamylo.CinemaBackend.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/showTimes")
public class ShowTimeController {
    @Autowired
    private ShowTimeService showTimeService;

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowTimeDto>> getShowTimesForMovie(@PathVariable Integer movieId) throws MovieException {
        List<ShowTime> showTimes = showTimeService.getShowTimeByMovieId(movieId);
        List<ShowTimeDto> showTimeDtos = ShowTimeDtoMapper.toShowTimeDtos(showTimes);
        return new ResponseEntity<>(showTimeDtos, HttpStatus.OK);
    }
}
