package kamylo.CinemaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatAvailabilityUpdateDto {
    private Integer showTimeId;
    private Integer seatId;
    private Integer seatNumber;
    private Integer rowNumber;
    private boolean reserved;
    private String action;
    private Integer reservationId;
}