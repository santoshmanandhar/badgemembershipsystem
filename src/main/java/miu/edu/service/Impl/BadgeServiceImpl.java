package miu.edu.service.Impl;

import lombok.RequiredArgsConstructor;
import miu.edu.domain.Badge;
import miu.edu.domain.Member;
import miu.edu.dto.BadgeDTO;
import miu.edu.dto.MemberDTO;
import miu.edu.dto.RequestBadgeDTO;
import miu.edu.repository.BadgeRepository;
import miu.edu.repository.MemberRepository;
import miu.edu.service.BadgeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;
    private final MemberRepository memberRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public BadgeDTO addBadge(RequestBadgeDTO requestBadgeDTO) {
       Optional<Member> member = memberRepository.findById(requestBadgeDTO.getMemberId());
       if (member.isEmpty()) {
              throw new RuntimeException("Member not found");
       }
         Badge badge = new Badge();
        Optional<Badge> activeBadge = badgeRepository.findBadgeByMemberIdAndIsActive(requestBadgeDTO.getMemberId(), true);
        if (activeBadge.isPresent()) {
            activeBadge.get().setIsActive(false);
            badge.setMember(activeBadge.get().getMember());
            badge.setIsActive(true);
            badgeRepository.save(activeBadge.get());
            badgeRepository.save(badge);
        } else {
            badge.setMember(member.get());
            badge.setIsActive(true);
            badgeRepository.save(badge);
        }
        return modelMapper.map(badge, BadgeDTO.class);
    }

    @Override
    public List<BadgeDTO> findAllBadges() {
        List<BadgeDTO> badgeDTOS = new ArrayList<>();
        List<Badge> badges = badgeRepository.findAll();
        for (Badge badge : badges) {
            badgeDTOS.add(modelMapper.map(badge, BadgeDTO.class));
        }
        return badgeDTOS;
    }

    @Override
    public List<BadgeDTO> findAllBadgesByMemberId(long id) {
        Optional<List<Badge>> memberBadges = badgeRepository.findBadgesByMember(id);
        if (memberBadges.isEmpty()) {
            throw new RuntimeException("Badge not found");
        } else {
            return memberBadges.get().stream()
                    .map(badge -> modelMapper.map(badge, BadgeDTO.class))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public BadgeDTO findActiveBadgeByMemberId(long id) {
        Optional<Badge> badge = badgeRepository.findBadgeByMemberIdAndIsActive(id, true);
        if (badge.isEmpty()) {
            throw new RuntimeException("Badge not found");
        } else {
            return modelMapper.map(badge.get(), BadgeDTO.class);
        }
    }

    @Override
    public BadgeDTO findBadgeById(long id) {
        Optional<Badge> badge = badgeRepository.findById(id);
        if (badge.isEmpty()) {
            throw new RuntimeException("Badge not found");
        } else {
            var member = badge.get().getMember();
            var badgeDTO = modelMapper.map(badge.get(), BadgeDTO.class);
            badgeDTO.setMemberDTO(modelMapper.map(member, MemberDTO.class));
            return badgeDTO;
            //return modelMapper.map(badge.get(), BadgeDTO.class);
        }

    }


    @Override
    public BadgeDTO updateBadge(long id, RequestBadgeDTO badge) {
        Optional<Badge> badgeOptional = badgeRepository.findById(id);
        if(badgeOptional.isEmpty()) {
            throw new RuntimeException("Badge not found");
        }
        if (badge.getIsActive()) {
            badgeRepository.updateBadgesToInactive(badge.getMemberId());
            badgeOptional.get().setIsActive(true);
            badgeRepository.save(badgeOptional.get());
        } else {
            badgeOptional.get().setIsActive(false);
            badgeRepository.save(badgeOptional.get());
        }
        return modelMapper.map(badgeOptional.get(), BadgeDTO.class);

    }

    @Override
    public void MakeBadgeInactive(long id) {
        try {
            Optional<Badge> badge = badgeRepository.findById(id);
            if (!badge.isPresent()) {
                throw new RuntimeException("Badge not found");
            } else {
                badge.get().setIsActive(false);
                badgeRepository.save(badge.get());
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to make badge inactive");
        }
    }
}
