package com.aldlflcl.location.domain.location;

import com.aldlflcl.location.domain.picture.Picture;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class LocationForm {


    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;

    @NotNull(message = "장소를 선택해주세요")
    private Float x, y;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;

    private List<MultipartFile> pictures;
}
