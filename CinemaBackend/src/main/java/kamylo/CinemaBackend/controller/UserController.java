package kamylo.CinemaBackend.controller;

import kamylo.CinemaBackend.dto.UserDto;
import kamylo.CinemaBackend.dto.mapper.UserDtoMapper;
import kamylo.CinemaBackend.exception.UserException;
import kamylo.CinemaBackend.model.User;
import kamylo.CinemaBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        if (token != null && token.startsWith("Bearer ")) {
            User user = userService.findUserProfileByJwt(token);
            UserDto userDto = UserDtoMapper.toUserDto(user);
            return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
