package miu.edu.service.Impl;
import miu.edu.adapter.LocationAdapter;
import miu.edu.adapter.PlanAdapter;
import miu.edu.domain.Location;
import miu.edu.domain.Plan;
import miu.edu.dto.LocationDTO;
import miu.edu.dto.PlanDTO;
import miu.edu.repository.PlanRepository;
import miu.edu.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    @Autowired
    PlanRepository planRepository;
    @Autowired
    PlanAdapter planAdapter;

    @Autowired
    LocationAdapter locationAdapter;
    @Override
    public List<PlanDTO> findAllPlans() {
       try {
           return planAdapter.entityToDtoAll(planRepository.findAll());
       } catch (RuntimeException e) {
           throw new RuntimeException("Failed to fetch plans");
       }
    }

    @Override
    public PlanDTO findPlanByID(Long id) {
        Plan plan = planRepository.findById(id).get();
        try {
            return planAdapter.entityToDto(plan);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to fetch plan with id " + id);
        }
    }

    @Override
    public PlanDTO addPlan(PlanDTO planDTO) {
        Plan plan = planAdapter.dtoToEntity(planDTO);
        try {
            planRepository.save(plan);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to add plan");
        }
        return planAdapter.entityToDto(plan);
    }

    @Override
    public PlanDTO updatePlan(Long id, PlanDTO planDTO) {
        Plan plan = planAdapter.dtoToEntity(planDTO);
        try {
            planRepository.save(plan);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to plan with id " + id);
        }

        return planDTO;
    }

    @Override
    public void deletePlan(Long id) {
        try {
            planRepository.deleteById(id);
        }catch (RuntimeException e) {
            throw new RuntimeException("Failed to remove plan with id " + id);
        }

    }

    @Override
    public List<LocationDTO> getAllLocationsOfPlan(Long planId) {

        try {
            Plan plan = planRepository.findById(planId).get();
            List<Location> locations = plan.getLocations();
            return  locationAdapter.entityToDtoAll(locations);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to fined plan for plan id " + planId);
        }
    }
}
