package miu.edu.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Badge {
    @Id
    @GeneratedValue
    @Column(name = "badge_id")
    private long id;

    @Column(name ="badge_status_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    public Badge(long id, Boolean isActive) {
        this.id = id;
        this.isActive = isActive;
    }
}
