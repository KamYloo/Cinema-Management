package kamylo.CinemaBackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int rowNumber;
    private int seatNumber;
    private boolean isReserved;

    @ManyToOne
    @JoinColumn(name = "showTime_id", nullable = false)
    private ShowTime showTime;
}
