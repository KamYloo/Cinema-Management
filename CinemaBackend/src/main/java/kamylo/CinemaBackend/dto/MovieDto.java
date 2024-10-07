package kamylo.CinemaBackend.dto;

import kamylo.CinemaBackend.model.ShowTime;
import lombok.Data;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
public class MovieDto {
    private Integer id;

    private String title;
    private String description;
    private String image;
    private String genre;
    private int duration;
    private UserDto user;
}
