package miu.edu.adapter;

import miu.edu.domain.Member;
import miu.edu.dto.MemberDTO;
import miu.edu.dto.MemberPlanDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberPlanAdapter {
    public MemberPlanDTO entityToDTO(Member member){
        return new MemberPlanDTO(member.getId(), member.getFirstname(), member.getLastname(), member.getEmail(),member.getPassword());
    }
    public List<MemberPlanDTO> entityToDTOAll(List<Member> members){
        return members.stream().map(member -> entityToDTO(member)).collect(Collectors.toList());
    }
    public Member DtoToEntity(MemberPlanDTO memberDTO){
        return new Member(memberDTO.getId(), memberDTO.getFirstname(), memberDTO.getLastname(), memberDTO.getEmail(),memberDTO.getPassword());
    }
    public List<Member> DtoToEntityAll(List<MemberPlanDTO> memberDTOS){
        return memberDTOS.stream().map(memberDTO -> DtoToEntity(memberDTO)).collect(Collectors.toList());
    }
}
