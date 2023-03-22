package miu.edu.service;

import miu.edu.dto.MembershipDTO;
import miu.edu.dto.MembershipPlanDTO;

import java.util.List;
import java.util.Optional;

public interface IMembershipService {
    Optional<MembershipDTO> getMembershipsById(Long id);

    MembershipPlanDTO createMembership(MembershipPlanDTO membershipDTO);
    MembershipPlanDTO updateMembership(MembershipPlanDTO membershipDTO);
    boolean deleteMembership(Long id);
    List<MembershipDTO> getAllMembership();
}
