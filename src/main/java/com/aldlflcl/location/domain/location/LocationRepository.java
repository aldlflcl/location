package com.aldlflcl.location.domain.location;

import com.aldlflcl.location.domain.picture.Picture;
import com.aldlflcl.location.domain.picture.PictureMapper;
import com.aldlflcl.location.domain.picture.PictureService;
import com.aldlflcl.location.domain.picture.Store;
import com.aldlflcl.location.domain.review.ReviewMapper;
import com.aldlflcl.location.domain.user.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationRepository {

    private final LocationMapper locationMapper;
    private final PictureMapper pictureMapper;
    private final ReviewMapper reviewMapper;
    private final PictureService pictureService;
    private final Store store;


    @Transactional
    public int insertLocation(LocationForm form, List<Picture> pictureList, LoginForm loginForm) {
        Location location = new Location(form);
        int id = locationMapper.insertLocation(location, loginForm);
        for (Picture picture : pictureList) {
            pictureMapper.insertPicture(location, picture);
        }
        return location.getLocationId();
    }

    public List<Location> locationList() {
        return locationMapper.locationList();
    }

    public Location getLocation(int id) {
        return locationMapper.getLocation(id);
    }

    @Transactional
    public int updateLocation(int locationId, LocationForm form, List<Picture> pictureList) {
        Location location = new Location(form);
        location.setLocationId(locationId);
        int id = locationMapper.updateLocation(location);
        for (Picture picture : pictureList) {
            pictureMapper.insertPicture(location, picture);
        }
        return location.getLocationId();
    }

    @Transactional
    public int updateLocation(int locationId, LocationForm form) {
        Location location = new Location(form);
        location.setLocationId(locationId);
        int id = locationMapper.updateLocation(location);
        return location.getLocationId();
    }

    @Transactional
    public void deleteLocation(Location location) throws IOException {
        List<Picture> pictures = location.getPictures();
        pictureService.deletePictures(pictures);
        pictureMapper.deleteByLocationId(location);
        reviewMapper.deleteByLocationId(location);
        locationMapper.deleteLocation(location);
    }

}
