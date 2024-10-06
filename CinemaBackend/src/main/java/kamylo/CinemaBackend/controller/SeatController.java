package kamylo.CinemaBackend.controller;

import kamylo.CinemaBackend.dto.SeatDto;
import kamylo.CinemaBackend.dto.mapper.SeatDtoMapper;
import kamylo.CinemaBackend.exception.ShowTimeException;
import kamylo.CinemaBackend.model.Seat;
import kamylo.CinemaBackend.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @GetMapping("/showtime/{showTimeId}")
    public ResponseEntity<List<SeatDto>> getSeatsForShowTime(@PathVariable Integer showTimeId) throws ShowTimeException {
        List<Seat> seats = seatService.getAvailableSeatsByShowTimeId(showTimeId);
        List<SeatDto> seatDtos = SeatDtoMapper.toSeatDtos(seats);
        return new ResponseEntity<>(seatDtos, HttpStatus.OK);
    }
}
