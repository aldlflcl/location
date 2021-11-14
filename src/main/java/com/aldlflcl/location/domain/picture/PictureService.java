package com.aldlflcl.location.domain.picture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PictureService {

    private final PictureRepository pictureRepository;
    private final Store store;

    @Transactional
    public void deletePicturesById(List<Integer> deletePictures) throws IOException {
        for (Integer deletePicture : deletePictures) {
            Picture picture = pictureRepository.getById(deletePicture);
            String address = picture.getAddress();
            String publicId = "Location/" + address.substring(address.lastIndexOf("/") + 1, address.lastIndexOf("."));
            pictureRepository.deletePicture(picture.getPictureId());
            log.info("pictureId={}", picture.getPictureId());
            Map delete = store.delete(publicId);
            log.info("publicId={}", publicId);
            log.info("deleteResult={}", delete.get("result"));
        }
    }

    @Transactional
    public void deletePictures(List<Picture> deletePictures) throws IOException {
        for (Picture picture : deletePictures) {
            String address = picture.getAddress();
            String publicId = "Location/" + address.substring(address.lastIndexOf("/") + 1, address.lastIndexOf("."));
            pictureRepository.deletePicture(picture.getPictureId());
            store.delete(publicId);
        }
    }
}
