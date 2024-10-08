package kamylo.CinemaBackend.dto;
import lombok.Data;

@Data
public class ReservationDto {
    private Integer id;

    private UserDto user;

    private ShowTimeDto showtime;

    private SeatDto seat;
}
