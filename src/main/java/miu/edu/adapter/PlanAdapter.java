package miu.edu.adapter;

import miu.edu.domain.Plan;
import miu.edu.dto.PlanDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
public class PlanAdapter {
    public Plan dtoToEntity(PlanDTO planDTO) {

        return  new Plan(planDTO.getId(), planDTO.getName(), planDTO.getDescription(), planDTO.getLocations());
    }
    public PlanDTO entityToDto(Plan plan) {
        return new PlanDTO(plan.getId(), plan.getName(), plan.getDescription(), plan.getLocations());
    }
    public List<Plan> dtoToEntityAll(List<PlanDTO> planDTOList) {
        return planDTOList.stream().map(planDto -> dtoToEntity(planDto)).collect(Collectors.toList());
    }
    public  List<PlanDTO> entityToDtoAll(List<Plan> planList) {
        return planList.stream().map(plan -> entityToDto(plan)).collect(Collectors.toList());
    }
}
