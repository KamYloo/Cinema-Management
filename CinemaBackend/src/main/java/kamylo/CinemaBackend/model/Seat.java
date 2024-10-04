package kamylo.CinemaBackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int rowNumber;
    private int seatNumber;
    private boolean isReserved;

    @ManyToOne
    @ToString.Exclude
    private ShowTime showTime;
}
