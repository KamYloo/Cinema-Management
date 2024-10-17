package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.config.JwtProvider;
import kamylo.CinemaBackend.exception.UserException;
import kamylo.CinemaBackend.model.User;
import kamylo.CinemaBackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplementationTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImplementation userService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testFindUserProfileByJwt_ValidJwt_ReturnsUser() throws UserException {
        String jwt = "valid.jwt.token";
        String email = "user@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);

        try (var mockedStaticJwtProvider = mockStatic(JwtProvider.class)) {
            when(JwtProvider.getEmailFromJwtToken(jwt)).thenReturn(email);
            when(userRepository.findByEmail(email)).thenReturn(mockUser);

            User result = userService.findUserProfileByJwt(jwt);

            assertNotNull(result);
            assertEquals(email, result.getEmail());
        }
    }

    @Test
    public void testFindUserProfileByJwt_InvalidJwt_ThrowsBadCredentialsException() {
        String invalidJwt = "invalid.jwt.token";

        try (var mockedStaticJwtProvider = mockStatic(JwtProvider.class)) {
            when(JwtProvider.getEmailFromJwtToken(invalidJwt)).thenReturn(null);

            assertThrows(BadCredentialsException.class, () -> userService.findUserProfileByJwt(invalidJwt));
        }
    }

    @Test
    public void testFindUserProfileByJwt_UserNotFound_ThrowsUserException() {
        String jwt = "valid.jwt.token";
        String email = "nonexistent@example.com";

        try (var mockedStaticJwtProvider = mockStatic(JwtProvider.class)) {
            when(JwtProvider.getEmailFromJwtToken(jwt)).thenReturn(email);
            when(userRepository.findByEmail(email)).thenReturn(null);

            UserException exception = assertThrows(UserException.class, () -> userService.findUserProfileByJwt(jwt));
            assertEquals("User not found with this email" + email, exception.getMessage());
        }
    }

    @Test
    public void testFindUserById_ValidId_ReturnsUser() throws UserException {
        Integer userId = 1;
        User mockUser = new User();
        mockUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User result = userService.findUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    public void testFindUserById_InvalidId_ThrowsUserException() {
        Integer invalidUserId = 999;

        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> userService.findUserById(invalidUserId));
        assertEquals("User not found with this id" + invalidUserId, exception.getMessage());
    }
}
