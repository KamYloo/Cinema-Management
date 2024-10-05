package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.config.JwtProvider;
import kamylo.CinemaBackend.exception.UserException;
import kamylo.CinemaBackend.model.User;
import kamylo.CinemaBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = JwtProvider.getEmailFromJwtToken(jwt);

        if (email==null) {
            throw new BadCredentialsException("Invalid JWT");
        }
        User user = userRepository.findByEmail(email);

        if(user==null) {
            throw new UserException("User not found with this email"+email);
        }
        return user;
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
       return userRepository.findById(userId).orElseThrow(() -> new UserException("User not found with this id"+userId));
    }
}
