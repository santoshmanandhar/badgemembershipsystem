package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import miu.edu.dto.LocationDTO;
import miu.edu.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    @PostMapping
    public ResponseEntity<?> addLocation(LocationDTO locationDTO){
        return new ResponseEntity<LocationDTO>(locationService.addLocation(locationDTO), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAllLocation(){
        return new ResponseEntity<List<LocationDTO>>(locationService.findAllLocations(), HttpStatus.OK);
    }
    @GetMapping("/{location_id}")
    public ResponseEntity<?> getLocation(@PathVariable Long location_id){
        return new ResponseEntity<LocationDTO>(locationService.findById(location_id), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<?> updateLocation(@Valid @RequestBody LocationDTO locationDTO){
        return new ResponseEntity<LocationDTO>(locationService.updateLocation(locationDTO), HttpStatus.OK);
    }
    @DeleteMapping("/{location_id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long location_id){
        return new ResponseEntity<String>(locationService.deleteById(location_id), HttpStatus.OK);
    }
}
