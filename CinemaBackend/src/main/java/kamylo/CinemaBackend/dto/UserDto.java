package kamylo.CinemaBackend.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String fullName;
    private boolean isAdmin;
    private boolean isReqUser;
}
