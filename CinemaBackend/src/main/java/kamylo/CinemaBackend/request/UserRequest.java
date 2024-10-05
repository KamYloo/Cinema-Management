package kamylo.CinemaBackend.request;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String password;
    private String fullName;
    private String role;
    private String artistName;
}