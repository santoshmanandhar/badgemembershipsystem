package miu.edu.adapter;

import miu.edu.domain.Member;
import miu.edu.dto.MemberDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
public class MemberAdapter {
    public MemberDTO entityToDTO(Member member){
        return new MemberDTO(member.getId(), member.getFirstname(), member.getLastname(), member.getEmail(), member.getPassword());
    }
    public List<MemberDTO> entityToDTOAll(List<Member> members){
        return members.stream().map(member -> entityToDTO(member)).collect(Collectors.toList());
    }
    public Member DtoToEntity(MemberDTO memberDTO){
        return new Member(memberDTO.getId(), memberDTO.getFirstname(), memberDTO.getLastname(), memberDTO.getEmail(), memberDTO.getPassword());
    }
    public List<Member> DtoToEntityAll(List<MemberDTO> memberDTOS){
        return memberDTOS.stream().map(memberDTO -> DtoToEntity(memberDTO)).collect(Collectors.toList());
    }
}
