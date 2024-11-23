package kamylo.CinemaBackend.service.impl;

import kamylo.CinemaBackend.config.JwtProvider;
import kamylo.CinemaBackend.exception.AuthException;
import kamylo.CinemaBackend.model.User;
import kamylo.CinemaBackend.repository.UserRepository;
import kamylo.CinemaBackend.request.UserRequest;
import kamylo.CinemaBackend.response.AuthResponse;
import kamylo.CinemaBackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserServiceImplementation customUserServiceImplementation;

    @Override
    public AuthResponse registerUser(UserRequest userRequest) throws AuthException {
        String role = userRequest.getRole().trim().toLowerCase();  // Normalize role input
        User isEmailExist = userRepository.findByEmail(userRequest.getEmail());

        if (isEmailExist != null) {
            throw new AuthException("Email is already used with another account");
        }

        User newUser = new User();
        newUser.setEmail(userRequest.getEmail());
        newUser.setFullName(userRequest.getFullName());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        if ("admin".equalsIgnoreCase(role)) {
            newUser.setRole("ADMIN");
        } else if ("user".equalsIgnoreCase(role)) {
            newUser.setRole("USER");
        } else {
            throw new AuthException("Invalid role provided. Must be 'Admin' or 'User'.");
        }

        User savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);

        return authResponse;
    }

    @Override
    public Authentication authenticateUser(String email, String password) {
        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(email);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid email and password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
