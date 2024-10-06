package kamylo.CinemaBackend.util;

import kamylo.CinemaBackend.model.Movie;
import kamylo.CinemaBackend.model.User;

public class MovieUtil {
    public static final boolean isReqAdminMovie (User user, Movie movie) {
        return (movie.getUser().getId().equals(user.getId()) && user.getRole().equalsIgnoreCase("ADMIN"));
    }
}
