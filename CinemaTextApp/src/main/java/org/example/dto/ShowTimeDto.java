package org.example.dto;

import java.time.LocalDateTime;

public class ShowTimeDto {
    private Integer id;
    private LocalDateTime time;
    private MovieDto movie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public MovieDto getMovie() {
        return movie;
    }

    public void setMovie(MovieDto movie) {
        this.movie = movie;
    }
}
