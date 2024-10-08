package kamylo.CinemaBackend.dto.mapper;

import kamylo.CinemaBackend.dto.ReservationDto;
import kamylo.CinemaBackend.dto.SeatDto;
import kamylo.CinemaBackend.dto.ShowTimeDto;
import kamylo.CinemaBackend.dto.UserDto;
import kamylo.CinemaBackend.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationDtoMapper {
    public static ReservationDto toreservationDto(Reservation reservation) {
        UserDto userDto = UserDtoMapper.toUserDto(reservation.getUser());
        SeatDto seatDto = SeatDtoMapper.toSeatDto(reservation.getSeat());
        ShowTimeDto showTimeDto = ShowTimeDtoMapper.toShowTimeDto(reservation.getShowtime());
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setUser(userDto);
        reservationDto.setSeat(seatDto);
        reservationDto.setShowtime(showTimeDto);
        return reservationDto;
    }

    public static List<ReservationDto> toreservationDtoList(List<Reservation> reservations) {
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDtoList.add(toreservationDto(reservation));
        }
        return reservationDtoList;
    }
}
