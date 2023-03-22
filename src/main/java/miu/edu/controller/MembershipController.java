package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import miu.edu.dto.MembershipDTO;
import miu.edu.dto.MembershipPlanDTO;
import miu.edu.service.IMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/memberships")
@RequiredArgsConstructor
public class MembershipController {

    private final IMembershipService iMembershipService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMembershipsById(@PathVariable("id") long id) {
        Optional<?> memberShipDTO = iMembershipService.getMembershipsById(id);
        if (memberShipDTO.isPresent()) {
            return new ResponseEntity<>(memberShipDTO.get(), HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping()
    public ResponseEntity<?> getAllMemberships() {
        var getList = iMembershipService.getAllMembership();
        if (getList != null) {
            return new ResponseEntity<>(getList, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping()
    @RolesAllowed("FACULTY  ")
    public ResponseEntity<?> createMemberships(@Valid @RequestBody MembershipPlanDTO membershipDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        } else {
            var createMemberships = iMembershipService.createMembership(membershipDTO);
            return new ResponseEntity<>("Membership created successfully", HttpStatus.CREATED);
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateMemberships(@Valid @RequestBody MembershipPlanDTO membershipDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        } else {
            var updateMemberships = iMembershipService.updateMembership(membershipDTO);
            if (updateMemberships != null) {
                return ResponseEntity.ok(iMembershipService.updateMembership(membershipDTO));
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberships(@PathVariable("id") long id) {
        boolean deleted = iMembershipService.deleteMembership(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
