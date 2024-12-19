package kamylo.CinemaBackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private String image;
    private String genre;
    private int duration;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private User user;


    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowTime> showTimes = new ArrayList<>();
}
