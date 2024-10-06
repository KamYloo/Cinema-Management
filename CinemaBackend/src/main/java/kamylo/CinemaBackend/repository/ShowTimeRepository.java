package kamylo.CinemaBackend.repository;

import kamylo.CinemaBackend.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Integer> {

    @Query("select s from ShowTime s where s.movie.id = :movieId")
    List<ShowTime> findByMovieId(@Param("movieId") Integer movieId);
}
