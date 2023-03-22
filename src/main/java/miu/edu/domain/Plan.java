package miu.edu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import miu.edu.domain.enums.MembershipType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Plan {

    @Id
    @GeneratedValue
    @Column(name = "plan_id")
    private long id;

    @Column(name = "plan_name", nullable = false)
    @NotBlank(message = "Plan name is required")
    private String name;

    @Column(name = "plan_description", nullable = false)
    @NotBlank(message = "Plan description is required")
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "plan_id")
    private List<Location> locations = new ArrayList<>();


    public Plan(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;

    }
}
