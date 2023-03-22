package miu.edu.adapter;

import miu.edu.domain.Badge;
import miu.edu.dto.RequestBadgeDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BadgeDtosAdapter {

    public RequestBadgeDTO entityToDTO(Badge badge){
        return new RequestBadgeDTO(badge.getId(), badge.getIsActive());
    }
    public Badge dtoToEntity(RequestBadgeDTO badgeDTO){
        return new Badge(badgeDTO.getId(), badgeDTO.getIsActive());
    }
    public List<RequestBadgeDTO> entityToDTOAll(List<Badge> badges){
        return badges.stream().map(badge -> entityToDTO(badge)).collect(Collectors.toList());
    }
    public List<Badge> dtoToEntityAll(List<RequestBadgeDTO> badgeDTOS){
        return badgeDTOS.stream().map(badgeDTO -> dtoToEntity(badgeDTO)).collect(Collectors.toList());
    }
}
