package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.AuthException;
import kamylo.CinemaBackend.request.UserRequest;
import kamylo.CinemaBackend.response.AuthResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    AuthResponse registerUser(UserRequest userRequest) throws AuthException;
    Authentication authenticateUser(String email, String password);
}
