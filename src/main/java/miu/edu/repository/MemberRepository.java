package miu.edu.repository;

import miu.edu.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT p FROM Membership ms JOIN ms.plan p WHERE ms.member.id = :id")
    List<Plan> findPlansByMemberId(@Param("id") Long id);

    @Query("SELECT b FROM Badge b Join b.member m where m.id = :id")
     List<Badge> findBadgeByMemberId(@Param("id") Long id);

    @Query("SELECT m FROM Membership m JOIN m.member b WHERE m.member.id = :id")
    List<Membership> findMembershipsByMemberId(@Param("id") Long id);

    @Query("SELECT b FROM Badge b JOIN b.member m WHERE m.id = :id AND b.id = :badgeId")
    Badge findByIdAndBadgesId(@Param("id") Long id, @Param("badgeId") Long badgeId);

    @Query("SELECT t FROM Transaction t where t.membership.member.id = :id")
    List<Transaction> findTransactionsByMemberId(@Param("id") Long id);

    Optional<Member> findByEmail(String email);

}
