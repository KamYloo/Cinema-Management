package kamylo.CinemaBackend.dto;
import lombok.Data;

@Data
public class SeatDto {
    private Integer id;

    private int rowNumber;
    private int seatNumber;
    private boolean isReserved;
//    private ShowTimeDto showTime;
}
