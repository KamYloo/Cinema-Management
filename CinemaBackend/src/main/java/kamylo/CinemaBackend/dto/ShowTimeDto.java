package kamylo.CinemaBackend.dto;

import jakarta.persistence.ManyToOne;
import kamylo.CinemaBackend.model.Movie;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
public class ShowTimeDto {
    private Integer id;
    private LocalDateTime time;
    private MovieDto movie;
}
