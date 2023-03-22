package miu.edu.adapter;

import miu.edu.domain.Membership;
import miu.edu.dto.MembershipDTO;
import miu.edu.dto.MembershipPlanDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MembershipPlanAdapter {
    public MembershipPlanDTO entityToDTO(Membership membership){
        return new MembershipPlanDTO(membership.getId(), membership.getStartDate(), membership.getEndDate(),membership.getLimit(),membership.getAllowMultiple(),membership.getMembershipType(),membership.getDurationType());
    }
    public List<MembershipPlanDTO> entityToDTOAll(List<Membership> membership){
        return membership.stream().map(memberships -> entityToDTO(memberships)).collect(Collectors.toList());
    }
    public Membership dtoToEntity(MembershipPlanDTO membershipDTO){
        return new Membership(membershipDTO.getId(), membershipDTO.getStartDate(), membershipDTO.getEndDate(),membershipDTO.getLimit(),membershipDTO.getAllowMultiple(),membershipDTO.getMembershipType(),membershipDTO.getDurationType());
    }
    public List<Membership> dtoToEntityAll(List<MembershipPlanDTO> membershipDTO){
        return membershipDTO.stream().map(memberDTO -> dtoToEntity(memberDTO)).collect(Collectors.toList());
    }
}
