package miu.edu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.domain.enums.RoleType;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "role",nullable = false)
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role(long id) {
        this.id = id;
    }


//    public Role(RoleType roleType) {
//        this.name = roleType.name();
//    }

    @Override
    public String toString() {
        return this.name;
    }
}
