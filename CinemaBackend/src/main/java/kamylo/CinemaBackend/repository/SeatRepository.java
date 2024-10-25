package kamylo.CinemaBackend.repository;

import kamylo.CinemaBackend.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    @Query("select s from Seat s where s.showTime.id = :showTimeId ORDER BY s.rowNumber, s.seatNumber")
    List<Seat> findByShowTimeId(@Param("showTimeId") Integer showTimeId);
}
