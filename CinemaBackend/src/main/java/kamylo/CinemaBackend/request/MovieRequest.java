package kamylo.CinemaBackend.request;

import kamylo.CinemaBackend.model.ShowTime;
import kamylo.CinemaBackend.model.User;
import lombok.Data;

import java.time.Duration;
import java.util.List;

@Data
public class MovieRequest {
    private String title;
    private String description;
    private String image;
    private String genre;
    private Duration duration;
    private User user;
    private List<ShowTime> showTimes;
}
