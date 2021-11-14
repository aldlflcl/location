package com.aldlflcl.location.domain.picture;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PictureRepository {

    private final PictureMapper pictureMapper;

    public Picture getById(int id) {
        return pictureMapper.getById(id);
    }

    @Transactional
    public void deletePicture(int pictureId) {
        pictureMapper.deletePicture(pictureId);
    }
}
