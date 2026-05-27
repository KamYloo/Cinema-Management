package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.ReservationException;
import kamylo.CinemaBackend.exception.SeatException;
import kamylo.CinemaBackend.exception.UserException;
import kamylo.CinemaBackend.model.Reservation;
import kamylo.CinemaBackend.model.Seat;
import kamylo.CinemaBackend.model.User;
import kamylo.CinemaBackend.repository.ReservationRepository;
import kamylo.CinemaBackend.repository.SeatRepository;
import kamylo.CinemaBackend.service.impl.ReservationServiceImplementation;
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

public class ReservationServiceImplementationTest {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private SeatService seatService;

    @Mock
    private UserService userService;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private ReservationServiceImplementation reservationService;

    private Seat seat;
    private User user;
    private Reservation reservation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        
        seat = new Seat();
        seat.setId(1);
        seat.setReserved(false);

        
        user = new User();
        user.setId(1);
        user.setFullName("John Doe");

        
        reservation = new Reservation();
        reservation.setId(1);
        reservation.setSeat(seat);
        reservation.setUser(user);
        reservation.setShowtime(seat.getShowTime());
    }

    @Test
    public void testCreateReservation_Successful() throws SeatException {
        
        when(seatService.getSeat(1)).thenReturn(seat);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(seatRepository.save(seat)).thenReturn(seat);

        
        Reservation result = reservationService.createReservation(1, user);

        
        assertNotNull(result);
        assertEquals(seat, result.getSeat());
        assertEquals(user, result.getUser());
        assertTrue(seat.isReserved());
        verify(seatService, times(1)).getSeat(1);
        verify(seatRepository, times(1)).save(seat);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testCreateReservation_SeatAlreadyReserved() throws SeatException {
        
        seat.setReserved(true); 
        when(seatService.getSeat(1)).thenReturn(seat);

        
        SeatException thrown = assertThrows(SeatException.class, () -> {
            reservationService.createReservation(1, user);
        });

        assertEquals("Seat already reserved", thrown.getMessage());
        verify(seatService, times(1)).getSeat(1);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    public void testGetReservationsByUserId_UserHasReservations() throws UserException {
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        user.setReservations(reservations);
        when(userService.findUserById(1)).thenReturn(user);

        
        List<Reservation> result = reservationService.getReservationsByUserId(1);

        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reservation, result.get(0));
        verify(userService, times(1)).findUserById(1);
    }

    @Test
    public void testGetReservationsByUserId_UserHasNoReservations() throws UserException {
        
        user.setReservations(null); 
        when(userService.findUserById(1)).thenReturn(user);

        
        List<Reservation> result = reservationService.getReservationsByUserId(1);

        
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userService, times(1)).findUserById(1);
    }

    @Test
    public void testGetReservationById_Successful() throws ReservationException {
        
        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

        
        Reservation result = reservationService.getReservationById(1);

        
        assertNotNull(result);
        assertEquals(reservation, result);
        verify(reservationRepository, times(1)).findById(1);
    }

    @Test
    public void testGetReservationById_ReservationNotFound() {
        
        when(reservationRepository.findById(1)).thenReturn(Optional.empty());

        
        ReservationException thrown = assertThrows(ReservationException.class, () -> {
            reservationService.getReservationById(1);
        });

        assertEquals("Reservation not found with id 1", thrown.getMessage());
        verify(reservationRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteReservationById_Successful() throws ReservationException, SeatException, UserException {
        
        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));
        when(seatService.getSeat(1)).thenReturn(seat);

        
        reservationService.deleteReservationById(1, 1);

        
        assertFalse(seat.isReserved());
        verify(reservationRepository, times(1)).deleteById(1);
        verify(seatRepository, times(1)).save(seat);
    }

    @Test
    public void testDeleteReservationById_ReservationNotFound() {
        
        when(reservationRepository.findById(1)).thenReturn(Optional.empty());

        
        ReservationException thrown = assertThrows(ReservationException.class, () -> {
            reservationService.deleteReservationById(1, 1);
        });

        assertEquals("Reservation not found with id 1", thrown.getMessage());
        verify(reservationRepository, times(1)).findById(1);
        verify(reservationRepository, never()).deleteById(1);
    }

    @Test
    public void testDeleteReservationById_UserNotAuthorized() throws ReservationException {
        
        User anotherUser = new User();
        anotherUser.setId(2); 
        reservation.setUser(anotherUser);
        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

        
        UserException thrown = assertThrows(UserException.class, () -> {
            reservationService.deleteReservationById(1, 1); 
        });

        assertEquals("User not authorized to delete reservation", thrown.getMessage());
        verify(reservationRepository, times(1)).findById(1);
        verify(reservationRepository, never()).deleteById(1);
    }

}

