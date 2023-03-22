package miu.edu.service;

import miu.edu.dto.LocationDTO;

import java.util.List;

public interface LocationService {
    public LocationDTO addLocation(LocationDTO locationDTO);
    public List<LocationDTO> findAllLocations();
    public LocationDTO findById(Long id);
    public LocationDTO updateLocation(LocationDTO locationDTO);
    public String deleteById(Long id);
}
