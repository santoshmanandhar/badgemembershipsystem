package miu.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.domain.Audit;
import miu.edu.domain.Badge;
import miu.edu.domain.Membership;
import miu.edu.domain.Role;

import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String  email;
    private String  password;
  //  private List<RoleDTO> roleTypes = new ArrayList<>();
    private Set<RoleDTO> roleTypes = new HashSet<>();
    public MemberDTO(Long id ,String firstname, String lastname, String email,String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
}
