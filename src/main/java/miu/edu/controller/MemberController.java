package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import miu.edu.dto.MemberDTO;
import miu.edu.dto.MemberPlanDTO;
import miu.edu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<?> addMember(@Valid  @RequestBody MemberDTO memberDTO, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldErrors());
        } else {
            return new ResponseEntity<MemberDTO>(memberService.addMember(memberDTO), HttpStatus.OK);
        }

    }
    @GetMapping
    public ResponseEntity<?> getAllMembers(){
        return new ResponseEntity<List<MemberDTO>>(memberService.findAllMembers(), HttpStatus.OK);
    }
    @GetMapping("/{member_id}")
    public ResponseEntity<?> getMember(@PathVariable Long member_id){
        return new ResponseEntity<MemberDTO>(memberService.findById(member_id), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<?> updateMember(MemberDTO memberDTO){
        return new ResponseEntity<MemberDTO>(memberService.updateMember(memberDTO), HttpStatus.OK);
    }
    @DeleteMapping("/{member_id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id){
        return new ResponseEntity<String>(memberService.deleteById(id), HttpStatus.OK);
    }

    @GetMapping("/{member_id}/plans")
    public ResponseEntity<?> getPlansByMemberId(@PathVariable Long member_id){
        return new ResponseEntity<>(memberService.findPlansByMemberId(member_id), HttpStatus.OK);
    }
    @GetMapping("/{id}/badges")
    public ResponseEntity<?> getBadgeByMemberId(@PathVariable("id") Long id){
        return new ResponseEntity<>(memberService.findBadgeByMemberId(id), HttpStatus.OK);
    }
    @GetMapping("/{id}/memberships")
    public ResponseEntity<?> getMembershipsByMemberId(@PathVariable("id") Long id){
        return new ResponseEntity<>(memberService.findMembershipsByMemberId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactionsByMemberId(@PathVariable("id") Long id){
        return new ResponseEntity<>(memberService.findTransactionsByMemberId(id), HttpStatus.OK);
    }
}
