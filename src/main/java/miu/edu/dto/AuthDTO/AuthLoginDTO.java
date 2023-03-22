package miu.edu.dto.AuthDTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginDTO {
    private String email;
    private String password;
}
