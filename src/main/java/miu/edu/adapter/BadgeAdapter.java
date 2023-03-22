package miu.edu.adapter;

import miu.edu.domain.Badge;
import miu.edu.dto.BadgeDTO;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BadgeAdapter {

    public BadgeDTO entityToDTO(Badge badge){
        return new BadgeDTO(badge.getId(), badge.getIsActive());
    }
    public Badge dtoToEntity(BadgeDTO badgeDTO){
        return new Badge(badgeDTO.getId(), badgeDTO.getIsActive());
    }
    public List<BadgeDTO> entityToDTOAll(List<Badge> badges){
        return badges.stream().map(badge -> entityToDTO(badge)).collect(Collectors.toList());
    }
    public List<Badge> dtoToEntityAll(List<BadgeDTO> badgeDTOS){
        return badgeDTOS.stream().map(badgeDTO -> dtoToEntity(badgeDTO)).collect(Collectors.toList());
    }
}
