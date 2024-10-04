package kamylo.CinemaBackend.model;

import jakarta.persistence.*;
import kamylo.CinemaBackend.config.User;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @ToString.Exclude
    private User user;

    @ManyToOne
    @ToString.Exclude
    private ShowTime showtime;

    @ManyToOne
    @ToString.Exclude
    private Seat seat;
}

