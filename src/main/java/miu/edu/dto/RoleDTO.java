package miu.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.domain.enums.RoleType;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private long id;
    private String name;

}
