package miu.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import miu.edu.domain.enums.DurationType;
import miu.edu.domain.enums.MembershipType;

import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MembershipPlanDTO {
    private long id;
    @Past(message = "Start date must be in the past")
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> planId = new ArrayList<>();
    private Long memberId;
    private Integer limit;
    private MembershipType membershipType;
    private DurationType durationType;

    private Boolean allowMultiple;

    public MembershipPlanDTO(long id, LocalDate startDate, LocalDate endDate,int limit,Boolean allowMultiple, MembershipType membershipType, DurationType durationType) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.limit = limit;
        this.allowMultiple = allowMultiple;
        this.membershipType = membershipType;
        this.durationType = durationType;
    }

    public MembershipPlanDTO(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MembershipDTO{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
               // ", planDTO=" + planDTO +
               // ", memberDTO=" + memberDTO +
                ", limit=" + limit +
                ", allowMultiple=" + allowMultiple +
                '}';
    }
}
