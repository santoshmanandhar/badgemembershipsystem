package miu.edu.adapter;

import miu.edu.domain.Location;
import miu.edu.dto.LocationDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class LocationAdapter {
    public LocationDTO entityToDto(Location location){
        return new LocationDTO(location.getId(), location.getName(), location.getDescription(), location.getCapacity(), location.getLocationType(), location.getTimeSlots());
    }
    public List<LocationDTO> entityToDtoAll(List<Location> locations){
        return locations.stream().map(location -> entityToDto(location)).collect(Collectors.toList());
    }
    public Location dtoToEntity(LocationDTO locationDTO){
        return new Location(locationDTO.getId(), locationDTO.getName(), locationDTO.getDescription(), locationDTO.getCapacity(), locationDTO.getLocationType(), locationDTO.getTimeSlots());
    }
    public List<Location> dtoToEntityAll(List<LocationDTO> locationDTOS){
        return locationDTOS.stream().map(locationDTO -> dtoToEntity(locationDTO)).collect(Collectors.toList());
    }
}
