package kamylo.CinemaBackend.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;

@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private String genre;
    private Duration duration;
}
