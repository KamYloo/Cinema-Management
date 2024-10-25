package kamylo.CinemaBackend.dto.mapper;

import kamylo.CinemaBackend.dto.SeatDto;
import kamylo.CinemaBackend.dto.ShowTimeDto;
import kamylo.CinemaBackend.model.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatDtoMapper {
    public static SeatDto toSeatDto(Seat seat) {
//        ShowTimeDto showTimeDto = ShowTimeDtoMapper.toShowTimeDto(seat.getShowTime());
        SeatDto seatDto = new SeatDto();
        seatDto.setId(seat.getId());
        seatDto.setSeatNumber(seat.getSeatNumber());
        seatDto.setReserved(seat.isReserved());
        seatDto.setRowNumber(seat.getRowNumber());
//        seatDto.setShowTime(showTimeDto);
        return seatDto;
    }

    public static List<SeatDto> toSeatDtos(List<Seat> seats) {
        List<SeatDto> seatDtos = new ArrayList<>();
        for (Seat seat : seats) {
            seatDtos.add(toSeatDto(seat));
        }
        return seatDtos;
    }
}
