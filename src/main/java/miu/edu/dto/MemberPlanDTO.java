package miu.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberPlanDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String  email;
    private String  password;
    private List<BadgeDTO> badgeDTOS = new ArrayList<>();
    private Set<RoleDTO> roleTypes = new HashSet<>();
    private List<MembershipDTO> membershipDTOS = new ArrayList<>();
    private List<Long> plansId = new ArrayList<>();
    public MemberPlanDTO(Long id , String firstname, String lastname, String email,String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
}
