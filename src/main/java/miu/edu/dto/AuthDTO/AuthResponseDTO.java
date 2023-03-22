package miu.edu.dto.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.domain.Role;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Set<Role> roles;
    private String token;

}
