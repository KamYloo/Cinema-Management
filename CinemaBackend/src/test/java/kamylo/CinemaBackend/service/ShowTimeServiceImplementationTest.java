package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.MovieException;
import kamylo.CinemaBackend.model.Seat;
import kamylo.CinemaBackend.model.ShowTime;
import kamylo.CinemaBackend.repository.SeatRepository;
import kamylo.CinemaBackend.repository.ShowTimeRepository;
import kamylo.CinemaBackend.service.impl.ShowTimeServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShowTimeServiceImplementationTest {
    @Mock
    private ShowTimeRepository showTimeRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private ShowTimeServiceImplementation showTimeService;

    private final int DEFAULT_SEAT_COUNT = 30;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetShowTimeByMovieId_WithExistingSeats() throws MovieException {
        // Arrange
        int movieId = 1;
        List<ShowTime> showTimes = new ArrayList<>();
        ShowTime showTimeWithSeats = new ShowTime();
        showTimeWithSeats.setSeats(createSeatsForShowTime(showTimeWithSeats, DEFAULT_SEAT_COUNT));
        showTimes.add(showTimeWithSeats);

        when(showTimeRepository.findByMovieId(movieId)).thenReturn(showTimes);

        // Act
        List<ShowTime> result = showTimeService.getShowTimeByMovieId(movieId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertFalse(result.get(0).getSeats().isEmpty());
        verify(seatRepository, never()).saveAll(anyList());
    }

    @Test
    public void testGetShowTimeByMovieId_WithNoSeats() throws MovieException {
        // Arrange
        int movieId = 1;
        List<ShowTime> showTimes = new ArrayList<>();
        ShowTime showTimeWithoutSeats = new ShowTime(); // No seats initialized
        showTimes.add(showTimeWithoutSeats);

        when(showTimeRepository.findByMovieId(movieId)).thenReturn(showTimes);

        // Act
        List<ShowTime> result = showTimeService.getShowTimeByMovieId(movieId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertFalse(result.get(0).getSeats().isEmpty()); // Seats should now be initialized
        verify(seatRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testInitializeSeatsForShowTime_CorrectSeatCountAndProperties() throws MovieException {
        int movieId = 1;
        List<ShowTime> showTimes = new ArrayList<>();
        ShowTime showTimeWithoutSeats = new ShowTime();
        showTimes.add(showTimeWithoutSeats);

        when(showTimeRepository.findByMovieId(movieId)).thenReturn(showTimes);

        // Act
        List<ShowTime> result = showTimeService.getShowTimeByMovieId(movieId);

        // Assert
        ShowTime showTime = result.get(0);
        List<Seat> seats = showTime.getSeats();
        assertEquals(30, seats.size(), "The number of initialized seats should be 30");

        int seatsPerRow = 6;
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            assertEquals(i % seatsPerRow + 1, seat.getSeatNumber(), "Seat number should match expected value");
            assertEquals(i / seatsPerRow + 1, seat.getRowNumber(), "Row number should match expected value");
            assertFalse(seat.isReserved(), "Seat should not be reserved by default");
        }

        verify(seatRepository, times(1)).saveAll(anyList());
    }


    // Helper method to create mock seats
    private List<Seat> createSeatsForShowTime(ShowTime showTime, int count) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Seat seat = new Seat();
            seat.setRowNumber((i - 1) / 10 + 1);
            seat.setSeatNumber(i);
            seat.setReserved(false);
            seat.setShowTime(showTime);
            seats.add(seat);
        }
        return seats;
    }
}
