package kamylo.CinemaBackend.repository;

import kamylo.CinemaBackend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findAllByOrderByIdDesc();

    @Query("select m from Movie m where lower(m.title) like lower(concat('%',:title,'%'))")
    Set<Movie> findByTitle(@Param("title") String title);
}
