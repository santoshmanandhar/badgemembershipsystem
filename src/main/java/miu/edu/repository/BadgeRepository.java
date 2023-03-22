package miu.edu.repository;

import miu.edu.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Optional<Badge> findBadgeByMemberIdAndIsActive(long id, boolean isActive);
    @Query("select b from Badge b where b.member.id = :id and b.isActive = true")
    Optional<List<Badge>> findBadgesByMember(@Param("id") long id);
    @Modifying
    @Query("update Badge b set b.isActive = false where b.member.id = :id")
    public void updateBadgesToInactive(@Param("id") long id);
}
