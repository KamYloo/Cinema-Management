package kamylo.CinemaBackend.dto.mapper;

import kamylo.CinemaBackend.dto.SeatDto;
import kamylo.CinemaBackend.model.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatDtoMapper {
    public static SeatDto toSeatDto(Seat seat) {
        SeatDto seatDto = new SeatDto();
        seatDto.setId(seat.getId());
        seatDto.setSeatNumber(seat.getSeatNumber());
        seatDto.setReserved(seat.isReserved());
        seatDto.setRowNumber(seat.getRowNumber());
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
