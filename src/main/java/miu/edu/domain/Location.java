package miu.edu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.domain.enums.LocationType;
import org.hibernate.validator.constraints.Normalized;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private long id;
    @Column(name = "location_name", nullable = false)
    @NotBlank(message = "Location name is required")
    private String name;
    @Column(name = "location_description", nullable = false)
    @NotBlank(message = "Location description is required")
    private String description;
    @Column(name = "location_capacity", nullable = false)
    @NotBlank(message = "Location capacity is required")
    private String capacity;

    @Column(name = "location_type", nullable = false)
    private LocationType locationType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private List<TimeSlot> timeSlots = new ArrayList<>();

    public Location(long id) {
        this.id = id;
    }

    @Embedded
    private Audit audit;

    public Location(long id, String name, String description, String capacity, LocationType locationType,List<TimeSlot> timeSlots) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.locationType = locationType;
        this.timeSlots = timeSlots;
    }
}
