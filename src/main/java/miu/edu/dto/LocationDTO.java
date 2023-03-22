package miu.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.domain.TimeSlot;
import miu.edu.domain.Transaction;
import miu.edu.domain.enums.LocationType;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private long id;
    private String name;
    private String description;
    private String capacity;
    private LocationType locationType;
    private List<TimeSlot> timeSlots = new ArrayList<>();

    public LocationDTO(long id) {
        this.id = id;
    }
}
