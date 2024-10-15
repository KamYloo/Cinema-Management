package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.SeatException;
import kamylo.CinemaBackend.exception.ShowTimeException;
import kamylo.CinemaBackend.model.Seat;
import kamylo.CinemaBackend.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SeatServiceImplementationTest {
    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatServiceImplementation seatService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAvailableSeatsByShowTimeId_ReturnsSeats() throws ShowTimeException {
        Integer showTimeId = 1;
        List<Seat> availableSeats = new ArrayList<>();
        Seat seat1 = new Seat();
        seat1.setRowNumber(1);
        seat1.setSeatNumber(1);
        seat1.setReserved(false);

        Seat seat2 = new Seat();
        seat2.setRowNumber(1);
        seat2.setSeatNumber(2);
        seat2.setReserved(false);

        availableSeats.add(seat1);
        availableSeats.add(seat2);

        when(seatRepository.findByShowTimeId(showTimeId)).thenReturn(availableSeats);

        //Act
        List<Seat> result = seatService.getAvailableSeatsByShowTimeId(showTimeId);

        //Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getSeatNumber());
        assertEquals(2, result.get(1).getSeatNumber());
        verify(seatRepository, times(1)).findByShowTimeId(showTimeId);
    }

    @Test
    public void testGetAvailableSeatsByShowTimeId_NoSeatsAvailable() throws ShowTimeException {
        Integer showTimeId = 1;
        when(seatRepository.findByShowTimeId(showTimeId)).thenReturn(new ArrayList<>());

        //Act
        List<Seat> result = seatService.getAvailableSeatsByShowTimeId(showTimeId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(seatRepository, times(1)).findByShowTimeId(showTimeId);
    }

    @Test
    public void testGetSeat_FoundSeat() throws SeatException {
        // Arrange
        Integer seatId = 1;
        Seat seat = new Seat();
        seat.setId(seatId);
        seat.setRowNumber(1);
        seat.setSeatNumber(1);
        seat.setReserved(false);

        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));

        // Act
        Seat result = seatService.getSeat(seatId);

        // Assert
        assertNotNull(result);
        assertEquals(seatId, result.getId());
        verify(seatRepository, times(1)).findById(seatId);
    }

    @Test
    public void testGetSeat_SeatNotFound() {
        // Arrange
        Integer seatId = 1;
        when(seatRepository.findById(seatId)).thenReturn(Optional.empty());

        // Act & Assert
        SeatException exception = assertThrows(SeatException.class, () -> seatService.getSeat(seatId));
        assertEquals("Seat not found with id: " + seatId, exception.getMessage());
        verify(seatRepository, times(1)).findById(seatId);
    }

}
