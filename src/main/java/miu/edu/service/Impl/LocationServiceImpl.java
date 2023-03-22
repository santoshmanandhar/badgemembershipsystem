package miu.edu.service.Impl;

import lombok.RequiredArgsConstructor;
import miu.edu.adapter.LocationAdapter;
import miu.edu.domain.Location;
import miu.edu.dto.LocationDTO;
import miu.edu.repository.LocationRepository;
import miu.edu.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationAdapter locationAdapter;

    private final LocationRepository locationRepository;

    @Override
    public LocationDTO addLocation(LocationDTO locationDTO) {
        try {
            locationRepository.save(locationAdapter.dtoToEntity(locationDTO));
            return locationDTO;
        }catch (RuntimeException e){
            throw new RuntimeException("Failed to add this location");
        }
    }
    @Override
    public List<LocationDTO> findAllLocations() {
        return locationAdapter.entityToDtoAll(locationRepository.findAll());
    }
    @Override
    public LocationDTO findById(Long id) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        Location location = locationOptional.orElseThrow(() -> new EntityNotFoundException("Location with id " + id + " not found"));
        return locationAdapter.entityToDto(location);
    }
    @Override
    public LocationDTO updateLocation(LocationDTO locationDTO) {
        try {
            locationRepository.save(locationAdapter.dtoToEntity(locationDTO));
            return locationDTO;
        }catch (RuntimeException e){
            throw new RuntimeException("Failed to update this location");
        }
    }
    @Override
    public String deleteById(Long id) {
        try {
            locationRepository.deleteById(id);
            return "Location deleted successfully";
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Failed to delete this member");
        }
    }
}
