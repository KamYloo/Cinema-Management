package org.example.dto;

public class ReservationDto {
    private Integer id;

    private UserDto user;

    private ShowTimeDto showtime;

    private SeatDto seat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public ShowTimeDto getShowtime() {
        return showtime;
    }

    public void setShowtime(ShowTimeDto showtime) {
        this.showtime = showtime;
    }

    public SeatDto getSeat() {
        return seat;
    }

    public void setSeat(SeatDto seat) {
        this.seat = seat;
    }
}
