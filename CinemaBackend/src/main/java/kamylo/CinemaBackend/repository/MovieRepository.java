package kamylo.CinemaBackend.repository;

import kamylo.CinemaBackend.model.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @EntityGraph(attributePaths = {"user"})
    List<Movie> findAllByOrderByIdDesc();

    @EntityGraph(attributePaths = {"user"})
    @Query("select m from Movie m where lower(m.title) like lower(concat('%',:title,'%'))")
    List<Movie> findByTitle(@Param("title") String title);
}
