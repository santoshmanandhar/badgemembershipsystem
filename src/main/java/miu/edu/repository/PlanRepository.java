package miu.edu.repository;

import miu.edu.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query("select p from Plan p where p.id = ?1")
    List<Plan> findPlansById(Long id);
}
