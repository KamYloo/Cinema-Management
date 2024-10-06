package kamylo.CinemaBackend.dto.mapper;

import kamylo.CinemaBackend.dto.UserDto;
import kamylo.CinemaBackend.model.User;

public class UserDtoMapper {
    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setAdmin(user.getRole().equalsIgnoreCase("ADMIN"));
        return userDto;
    }
}
