package miu.edu.repository;

import miu.edu.domain.Member;
import miu.edu.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    @Query("SELECT m FROM Membership m " +
            "JOIN m.member mem " +
            "JOIN m.plan p " +
            "JOIN p.locations l " +
            "WHERE mem.id = :member_id " +
            "AND p.id = :plan_id " +
            "AND l.id = :location_id")
    Membership findFirstByMemberAndPlanAndLocation(@Param("member_id") Long member_id,
                                                         @Param("plan_id") Long plan_id,
                                                         @Param("location_id") Long location_id);
//    @Query("SELECT m FROM Membership m WHERE m.member.id = :member_id")
//    List<Membership> findMembershipByMember(Long member_id);
}
