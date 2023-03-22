package miu.edu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import miu.edu.domain.enums.DurationType;
import miu.edu.domain.enums.MembershipType;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Membership {
    @Id
    @GeneratedValue
    @Column(name = "membership_id")
    private long id;

    @Past(message = "Start date must be in the past")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Future(message = "End date must be in the future")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "membership_plans",
            joinColumns = @JoinColumn(name = "membership_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_id"))
    private List<Plan> plan = new ArrayList<>();

    @Column(name = "membership_type", nullable = false)
    private MembershipType membershipType;

    @Column(name = "access_limit", nullable = true)
    private Integer limit;

    @Column(name = "allow_multiple", nullable = false)
    private Boolean allowMultiple;

    @Column(name = "duration_type", nullable = false)
    private DurationType durationType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    public Membership(long id, LocalDate startDate, LocalDate endDate, Integer limit, Boolean allowMultiple, MembershipType membershipType, DurationType durationType) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.limit = limit;
        this.allowMultiple = allowMultiple;
        this.membershipType = membershipType;
        this.durationType = durationType;
    }

    public Membership(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", plan=" + plan +
                ", membershipType=" + membershipType +
                ", limit=" + limit +
                ", allowMultiple=" + allowMultiple +
                ", durationType=" + durationType +
                ", member=" + member +
                '}';
    }
}
