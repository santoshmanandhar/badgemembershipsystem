package miu.edu.service;

import miu.edu.domain.Badge;
import miu.edu.domain.Member;
import miu.edu.dto.BadgeDTO;
import miu.edu.dto.RequestBadgeDTO;

import java.util.List;

public interface BadgeService {

    BadgeDTO addBadge(RequestBadgeDTO badge);
    List<BadgeDTO> findAllBadges();
    List<BadgeDTO> findAllBadgesByMemberId(long id);
    BadgeDTO findActiveBadgeByMemberId(long id);
    BadgeDTO findBadgeById(long id);
    BadgeDTO updateBadge(long id, RequestBadgeDTO badge);
    void MakeBadgeInactive(long id);
}
