package miu.edu.controller;
import miu.edu.dto.LocationDTO;
import miu.edu.dto.PlanDTO;
import miu.edu.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {
    @Autowired
    private PlanService planService;

    @GetMapping
    public ResponseEntity<?> getAllPlans() {
        return new ResponseEntity<List<PlanDTO>>(planService.findAllPlans(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlanByID(@PathVariable Long id) {
        return new ResponseEntity<PlanDTO>(planService.findPlanByID(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody PlanDTO planDTO) {
        return new ResponseEntity<PlanDTO>(planService.addPlan(planDTO), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody PlanDTO planDTO) {
        return new ResponseEntity<PlanDTO>(planService.updatePlan(id, planDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        planService.deletePlan(id);
    }

    @GetMapping("{planId}/locations")
    public ResponseEntity<?>getAllLocationsOfPlan(@PathVariable Long planId) {
        return new ResponseEntity<List<LocationDTO>>(planService.getAllLocationsOfPlan(planId), HttpStatus.OK);
    }
}
