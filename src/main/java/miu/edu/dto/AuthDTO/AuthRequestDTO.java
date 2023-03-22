package miu.edu.dto.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.domain.Role;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
    private String firstname;
    private String lastname;
    @NotEmpty(message = "Email is required")

    private String email;
    private Set<Role> roles;
    @Length(min = 6, message = "Password must be at least 6 characters")
    private String password;


}
