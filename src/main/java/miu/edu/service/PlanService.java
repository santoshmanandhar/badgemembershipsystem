package miu.edu.service;

import miu.edu.dto.LocationDTO;
import miu.edu.dto.PlanDTO;

import java.util.List;
public interface PlanService {
   public List<PlanDTO> findAllPlans();
   public PlanDTO findPlanByID(Long id);
   public PlanDTO addPlan(PlanDTO planDTO);
   public PlanDTO updatePlan(Long id, PlanDTO planDTO);
   public void deletePlan(Long id);

   List<LocationDTO> getAllLocationsOfPlan(Long planId);
}
