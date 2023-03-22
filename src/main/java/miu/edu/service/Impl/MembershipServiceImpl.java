package miu.edu.service.Impl;

import lombok.RequiredArgsConstructor;
import miu.edu.adapter.MemberAdapter;
import miu.edu.adapter.MembershipAdapter;
import miu.edu.adapter.MembershipPlanAdapter;
import miu.edu.adapter.PlanAdapter;
import miu.edu.domain.Membership;
import miu.edu.domain.Plan;
import miu.edu.dto.MembershipDTO;
import miu.edu.dto.MembershipPlanDTO;
import miu.edu.repository.MemberRepository;
import miu.edu.repository.MembershipRepository;
import miu.edu.repository.PlanRepository;
import miu.edu.service.IMembershipService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements IMembershipService {

    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;
    private final MembershipRepository membershipRepository;

    private final MembershipAdapter membershipAdapter;
    private final MembershipPlanAdapter membershipPlanAdapter;


    private final PlanAdapter planAdapter;

    private final MemberAdapter memberAdapter;

    @Override
    public Optional<MembershipDTO> getMembershipsById(Long id) {
        try {
            Optional<Membership> getMemberShipById = membershipRepository.findById(id);
            if (getMemberShipById.isPresent()) {
                var membershipDTO = membershipAdapter.entityToDTO(getMemberShipById.get());
                var planList = memberRepository.findPlansByMemberId(getMemberShipById.get().getMember().getId());
                var planDtoList = planAdapter.entityToDtoAll(planList);
                var memberDtoList = memberAdapter.entityToDTO(getMemberShipById.get().getMember());
                membershipDTO.setPlanDTO(planDtoList);
                membershipDTO.setMemberDTO(memberDtoList);
                return Optional.of(membershipDTO);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get the membership");

        }
    }

    @Override
    public MembershipPlanDTO createMembership(MembershipPlanDTO membershipDTO) {


        var membership = membershipPlanAdapter.dtoToEntity(membershipDTO);
        var member = memberRepository.findById(membershipDTO.getMemberId());
        List<Plan> plansLists = new ArrayList<>();
        for (var plan : membershipDTO.getPlanId()) {
            plansLists.add(planRepository.findById(plan).get());
        }
        membership.setPlan(plansLists);
        membership.setMember(member.get());
        membershipRepository.save(membership);
        var membershipDto = membershipAdapter.entityToDTO(membership);
        var planDtoList = planAdapter.entityToDtoAll(membership.getPlan());
        var memberDtoList = memberAdapter.entityToDTO(membership.getMember());
        membershipDto.setPlanDTO(planDtoList);
        membershipDto.setMemberDTO(memberDtoList);
        return membershipDTO;


    }


    @Override
    public MembershipPlanDTO updateMembership(MembershipPlanDTO membershipDTO) {
        try {
            Membership membershipEntity = membershipPlanAdapter.dtoToEntity(membershipDTO);
            Optional<Membership> membership = membershipRepository.findById(membershipEntity.getId());
            if (membership.isPresent()) {
                var update = createMembership(membershipDTO);
                return update;

            } else {
                throw new RuntimeException("Membership id not found");
            }


        } catch (Exception e) {
            throw new RuntimeException("Failed to update the membership");
        }
    }

    @Override
    public boolean deleteMembership(Long id) {
        try {
            var findMembership = membershipRepository.findById(id);
            if (findMembership.isPresent()) {
                membershipRepository.deleteById(id);
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete the membership");
        }
    }

    @Override
    public List<MembershipDTO> getAllMembership() {
        try {
            List<Membership> list = membershipRepository.findAll();
            var membershipList = membershipAdapter.entityToDTOAll(list);
            for (Membership membership : list) {
                var planList = planAdapter.entityToDtoAll(membership.getPlan());
                var memberList = memberAdapter.entityToDTO(membership.getMember());
                for (MembershipDTO membershipDTO : membershipList) {
                    if (membershipDTO.getId() == membership.getId()) {
                        membershipDTO.setPlanDTO(planList);
                        membershipDTO.setMemberDTO(memberList);
                    }
                }
            }
            return membershipList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all the membership");
        }
    }
}
