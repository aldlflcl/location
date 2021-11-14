package com.aldlflcl.location.domain.picture;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Store {

    private final Cloudinary cloudinary;

    public List<Picture> upload(List<MultipartFile> fileList) throws IOException {
        List<Picture> pictureList = new ArrayList<>();
        for (MultipartFile f : fileList) {
            Map uploadResult = cloudinary.uploader().upload(f.getBytes(), ObjectUtils.asMap(
                    "folder", "Location"
            ));
            Picture picture = new Picture();
            picture.setAddress((String) uploadResult.get("url"));
            pictureList.add(picture);
        }
        return pictureList;
    }

    public Map delete(String id) throws IOException {
        Map destroy = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return destroy;
    }

}
