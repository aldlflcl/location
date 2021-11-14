package com.aldlflcl.location.web.location;

import com.aldlflcl.location.domain.location.Location;
import com.aldlflcl.location.domain.location.LocationGeoData;
import com.aldlflcl.location.domain.location.LocationMapper;
import com.aldlflcl.location.domain.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationDataController {

    private final LocationMapper locationMapper;

    @GetMapping("/location/data")
    public List<LocationGeoData> locationData() {
        List<LocationGeoData> locations = locationMapper.locationGeoDataList();
        return locations;
    }
}
