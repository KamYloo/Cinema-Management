package kamylo.CinemaBackend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Data
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "showTime_id", nullable = false)
    private ShowTime showtime;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Seat seat;
}

