package kamylo.CinemaBackend.service;

import kamylo.CinemaBackend.exception.UserException;
import kamylo.CinemaBackend.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findUserProfileByJwt(String jwt) throws UserException;
    User findUserById(Integer userId) throws UserException;
}
