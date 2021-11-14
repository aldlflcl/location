package com.aldlflcl.location.web.location;

import com.aldlflcl.location.domain.location.Location;
import com.aldlflcl.location.domain.location.LocationForm;
import com.aldlflcl.location.domain.location.LocationRepository;
import com.aldlflcl.location.domain.picture.Picture;
import com.aldlflcl.location.domain.picture.PictureService;
import com.aldlflcl.location.domain.picture.Store;
import com.aldlflcl.location.domain.review.Review;
import com.aldlflcl.location.domain.user.LoginForm;
import com.aldlflcl.location.web.SessionConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final LocationRepository repository;
    private final Store store;
    private final PictureService pictureService;

    @GetMapping("/new")
    public String locationForm(@ModelAttribute("locationForm") LocationForm form) {

        return "main/new";
    }

    @PostMapping("/location")
    public String addLocation(@Validated @ModelAttribute LocationForm form, BindingResult bindingResult,
                              @SessionAttribute(name = SessionConstant.LOGIN_USER, required = false) LoginForm loginForm) throws IOException {

        List<MultipartFile> pictures = form.getPictures();

        if (pictures.get(0).isEmpty()) {
            bindingResult.rejectValue("pictures", null, "사진을 1장이상 첨부해주세요.");
        }

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "main/new";
        }

        List<Picture> upload = store.upload(pictures);
        int id = repository.insertLocation(form, upload, loginForm);

        return "redirect:/location/" + id;
    }

    @GetMapping("/location/{id}")
    public String location(@PathVariable int id, Model model, @ModelAttribute("review") Review review) {

        Location location = repository.getLocation(id);
        model.addAttribute("location", location);
        /*List<Picture> pictures = location.getPictures();*/

        return "main/location";
    }

    @DeleteMapping("/location/{id}")
    public String deleteLocation(@PathVariable int id, @SessionAttribute(name = SessionConstant.LOGIN_USER, required = true) LoginForm loginForm, Model model) throws IOException {

        Location location = repository.getLocation(id);

        int author = location.getUser().getUserId();
        int loginUser = loginForm.getUserId();

        if (author != loginUser) { // 작성자와 로그인한 유저가 서로 다를때
            model.addAttribute("location", location);
            return "redirect:/location/" + id;
        }

        repository.deleteLocation(location);

        return "redirect:/";
    }

    @GetMapping("/location/{locationId}/edit")
    public String editLocationForm(@PathVariable int locationId, @ModelAttribute("locationForm") LocationForm locationForm,
                                   Model model,
                                   @SessionAttribute(name = SessionConstant.LOGIN_USER, required = true) LoginForm loginForm) {
        if (loginForm == null) {
            return "redirect:/location/" + locationId;
        }
        Location location = repository.getLocation(locationId);
        if (location.getUser().getUserId() != loginForm.getUserId()) {
            return "redirect:/location/" + locationId;
        }


        locationForm.setTitle(location.getTitle());
        locationForm.setContent(location.getContent());
        locationForm.setX(location.getAddrX());
        locationForm.setY(location.getAddrY());

        model.addAttribute("location", location);

        return "main/edit";
    }

    @PutMapping("/location/{locationId}/edit")
    public String editLocation(@PathVariable int locationId, @Validated @ModelAttribute("locationForm") LocationForm locationForm,
                               BindingResult bindingResult,
                               @SessionAttribute(name = SessionConstant.LOGIN_USER, required = true) LoginForm loginForm,
                               @RequestParam(name = "deletePictures", defaultValue = "") List<Integer> deletePictures,
                               Model model) throws IOException {
        if (loginForm == null) {
            return "redirect:/location/" + locationId;
        }

        Location location = repository.getLocation(locationId);

        if (loginForm.getUserId() != location.getUser().getUserId()) { // 작성자가 아닐때
            log.info("글 작성자랑 로그인한 사용자랑 다름");
            return "redirect:/location/" + locationId;
        }

        List<MultipartFile> pictures = locationForm.getPictures(); // 추가한 사진리스트

        log.info("삭제할사진 개수={}", deletePictures.size());

        if (location.getPictures().size() - (pictures.size() - deletePictures.size()) < 0) {
            log.info("사진이 1미만임");
            bindingResult.rejectValue("pictures", null, "사진을 1장이상 첨부해주세요.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("location", location);
            return "main/edit";
        }

        if (!pictures.get(0).isEmpty()) {
            List<Picture> upload = store.upload(pictures); // 추가한 사진 클라우드에 업로드
            repository.updateLocation(locationId, locationForm, upload); // location테이블 업데이트 picture테이블 업로드
        } else {
            repository.updateLocation(locationId, locationForm); //사진추가 없으면 location테이블만 업데이트
        }

        if (deletePictures.size() > 0) {
            log.info("db에서 사진삭제");
            pictureService.deletePicturesById(deletePictures); // db와 클라우드에서 사진 삭제
        }

        return "redirect:/location/" + locationId;
    }


}
