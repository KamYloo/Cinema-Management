package kamylo.CinemaBackend.controller;

import kamylo.CinemaBackend.config.JwtProvider;
import kamylo.CinemaBackend.model.User;
import kamylo.CinemaBackend.repository.UserRepository;
import kamylo.CinemaBackend.request.UserRequest;
import kamylo.CinemaBackend.response.AuthResponse;
import kamylo.CinemaBackend.service.CustomUserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserServiceImplementation customUserServiceImplementation;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserRequest userRequest) throws Exception {
        String role = userRequest.getRole().trim().toLowerCase();  // Normalize role input

        System.out.println("Role received from request: " + role);
        User isEmailExist = userRepository.findByEmail(userRequest.getEmail());
        if (isEmailExist != null) {
            throw new Exception("Email Is Already Used With Another Account");
        }
        User createdUser = new User();
        createdUser.setEmail(userRequest.getEmail());
        createdUser.setFullName(userRequest.getFullName());
        createdUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        if ("admin".equalsIgnoreCase(role)) {
            createdUser.setRole("ADMIN");
        } else if ("user".equalsIgnoreCase(role)) {
            createdUser.setRole("USER");
        } else {
            throw new Exception("Invalid role provided. Must be 'Admin' or 'User'.");
        }

        User savedUser = userRepository.save(createdUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> signin(@RequestBody User loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println(email + "-------" + password);

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String email, String password) {

        System.out.println(email + "---++----" + password);

        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(email);

        System.out.println("Sig in in user details" + userDetails);

        if (userDetails == null) {
            System.out.println("Sign in details - null" + userDetails);

            throw new BadCredentialsException("Invalid email and password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("Sign in userDetails - password mismatch" + userDetails);

            throw new BadCredentialsException("Invalid password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }
}
