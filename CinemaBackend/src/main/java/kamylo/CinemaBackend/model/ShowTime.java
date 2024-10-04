package kamylo.CinemaBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime time;

    @ManyToOne
    @ToString.Exclude
    private Movie movie;

    @JsonIgnore
    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Seat> seats = new ArrayList<>();

}
