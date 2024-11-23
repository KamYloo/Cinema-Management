package kamylo.CinemaBackend.controller;

import kamylo.CinemaBackend.dto.ReservationDto;
import kamylo.CinemaBackend.dto.mapper.ReservationDtoMapper;
import kamylo.CinemaBackend.exception.ReservationException;
import kamylo.CinemaBackend.exception.SeatException;
import kamylo.CinemaBackend.exception.UserException;
import kamylo.CinemaBackend.model.Reservation;
import kamylo.CinemaBackend.model.User;
import kamylo.CinemaBackend.response.ApiResponse;
import kamylo.CinemaBackend.service.ReservationService;
import kamylo.CinemaBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ReservationDto> createReservationHandler(@RequestBody Map<String, Integer> requestBody, @RequestHeader("Authorization") String token) throws UserException, SeatException {
        Integer seatId = requestBody.get("seatId");
        User user = userService.findUserProfileByJwt(token);
        Reservation reservation = reservationService.createReservation(seatId, user);
        ReservationDto reservationDto = ReservationDtoMapper.toreservationDto(reservation);
        return new ResponseEntity<>(reservationDto, HttpStatus.CREATED);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDto> getMovieHandler(@PathVariable Integer reservationId) throws ReservationException {
        Reservation reservation = reservationService.getReservationById(reservationId);
        ReservationDto reservationDto = ReservationDtoMapper.toreservationDto(reservation);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDto>> getReservationsByUserHandler(@PathVariable("userId") Integer userId) throws UserException {
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        List<ReservationDto> reservationDtos = ReservationDtoMapper.toreservationDtoList(reservations);
        return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{reservationId}")
    public ResponseEntity<ApiResponse> deleteAlbumHandler(@PathVariable Integer reservationId , @RequestHeader("Authorization") String token) throws UserException, SeatException {
        User user = userService.findUserProfileByJwt(token);
        ApiResponse res = new ApiResponse();

        try {
            reservationService.deleteReservationById(reservationId, user.getId());
            res.setMessage("Reservation deleted successfully.");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (UserException | ReservationException e) {
            res.setMessage(e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
        }
    }
}
