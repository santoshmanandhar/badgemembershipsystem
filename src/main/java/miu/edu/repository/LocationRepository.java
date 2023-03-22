package miu.edu.repository;

import miu.edu.domain.Location;
import miu.edu.domain.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("SELECT ts FROM Location l JOIN l.timeSlots ts WHERE l.id = :location_id AND :currentTime BETWEEN ts.startTime AND ts.endTime")
    TimeSlot findFirstByTimeSlotsByLocationAndTime(@Param("location_id") Long location_id, @Param("currentTime") LocalDateTime currentTime);

}
