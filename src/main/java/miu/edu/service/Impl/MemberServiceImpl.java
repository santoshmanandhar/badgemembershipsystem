package miu.edu.service.Impl;

import lombok.RequiredArgsConstructor;
import miu.edu.adapter.*;
import miu.edu.domain.Audit;
import miu.edu.domain.Member;
import miu.edu.domain.Transaction;
import miu.edu.dto.*;
import miu.edu.repository.MemberRepository;
import miu.edu.service.MemberService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberAdapter memberAdapter;
    private final RequestBadgeDTOAdapter requestBadgeDTOAdapter;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final BadgeAdapter badgeAdapter;
    private final MembershipAdapter membershipAdapter;
    private final RoleAdapter roleAdapter;
    private final PlanAdapter planAdapter;
    private final TransactionAdapter transactionAdapter;

    @Override
    public MemberDTO addMember(MemberDTO memberDTO) {


        var member = memberAdapter.DtoToEntity(memberDTO);

        String password = bCryptPasswordEncoder.encode(memberDTO.getPassword());
        member.setPassword(password);
        var roleTypes = roleAdapter.dtoToEntityAll(memberDTO.getRoleTypes());
        member.setRoleTypes(roleTypes);
        member.setAudit(new Audit(LocalDateTime.now()));
        memberRepository.save(member);
        return memberAdapter.entityToDTO(member);

    }

    @Override
    public List<MemberDTO> findAllMembers() {
        try {
            return memberAdapter.entityToDTOAll(memberRepository.findAll());
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to find the members");
        }
    }

    @Override
    public MemberDTO findById(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        Member member = memberOptional.orElseThrow(() -> new EntityNotFoundException("Member with id " + id + " not found"));
        return memberAdapter.entityToDTO(member);
    }

    @Override
    public MemberDTO updateMember(MemberDTO memberDTO) {
        try {
            memberRepository.save(memberAdapter.DtoToEntity(memberDTO));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Failed to update the member");
        }
        return memberDTO;
    }

    @Override
    public String deleteById(Long id) {
        try {
            memberRepository.deleteById(id);
            return "Member deleted successfully";
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Failed to delete this member");
        }
    }

    @Override
    public List<PlanDTO> findPlansByMemberId(Long id) {
        try {
            var plansByMemberId = memberRepository.findPlansByMemberId(id);
            return planAdapter.entityToDtoAll(plansByMemberId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to find the plans");
        }
    }

    @Override
    public List<RequestBadgeDTO> findBadgeByMemberId(Long id) {
        try {
            var badgeByMemberId = memberRepository.findBadgeByMemberId(id);
            var member = memberRepository.findById(id).get();
            var badgeDto = requestBadgeDTOAdapter.entityToDTOAll(badgeByMemberId);
            badgeDto.forEach(badgeDTO -> badgeDTO.setMemberId(member.getId()));
            return badgeDto;

        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to find the badge");
        }
    }

    @Override
    public List<MembershipDTO> findMembershipsByMemberId(Long id) {
        try {
            var membershipsByMemberId = memberRepository.findMembershipsByMemberId(id);
            List<MembershipDTO> membershipDTOS = membershipsByMemberId.stream()
                    .flatMap(membership -> {
                        var membershipDTO = membershipAdapter.entityToDTO(membership);
                        var memberDTO = memberAdapter.entityToDTO(membership.getMember());
                        var planDTO = planAdapter.entityToDtoAll(membership.getPlan());
                        membershipDTO.setMemberDTO(memberDTO);
                        membershipDTO.setPlanDTO(planDTO);
                        return Stream.of(membershipDTO);
                    }).collect(Collectors.toList());

            return membershipDTOS;

        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to find the badge");
        }
    }

    @Override
    public List<TransactionDTO> findTransactionsByMemberId(Long id) {
        try {
            List<Transaction> transactions = memberRepository.findTransactionsByMemberId(id);
            return transactionAdapter.entityToDtoAll(transactions);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to find the transations.");
        }
    }


}
