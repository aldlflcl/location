package com.aldlflcl.location.web.review;

import com.aldlflcl.location.domain.review.Review;
import com.aldlflcl.location.domain.review.ReviewMapper;
import com.aldlflcl.location.domain.review.ReviewRepository;
import com.aldlflcl.location.domain.user.LoginForm;
import com.aldlflcl.location.domain.user.User;
import com.aldlflcl.location.domain.user.UserMapper;
import com.aldlflcl.location.domain.user.UserRepository;
import com.aldlflcl.location.web.SessionConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/location/{id}/review")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @PostMapping
    public String review(@PathVariable int id, @Validated @ModelAttribute Review review, BindingResult bindingResult,
                         @SessionAttribute(name = SessionConstant.LOGIN_USER,required = false)LoginForm loginForm,
                         RedirectAttributes redirectAttributes) {

        if (loginForm == null) {
            return "redirect:/location/" + id;
        }

        if (bindingResult.hasErrors()) {
            log.info("reviewError={}", bindingResult);
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/location/" + id;
        }

        reviewRepository.createReview(loginForm, id, review);
        redirectAttributes.addFlashAttribute("success", "리뷰가 작성되었습니다.");
        return "redirect:/location/" + id;
    }

    @DeleteMapping("/{reviewId}")
    public String deleteReview(@PathVariable int id, @PathVariable int reviewId,
                               @SessionAttribute(name = SessionConstant.LOGIN_USER,required = false)LoginForm loginForm,
                               RedirectAttributes redirectAttributes) {
        if (loginForm == null) {
            return "redirect:/location/" + id;
        }

        //세션에 있는 유저가 삭제할 리뷰를 작성했는지 확인
        User user = userRepository.getById(loginForm.getUserId());

        List<Review> reviews = user.getReviews();
        for (Review review : reviews) {
            if (review.getReviewId() == reviewId) {
                reviewRepository.deleteReview(reviewId);
                redirectAttributes.addFlashAttribute("success", "리뷰가 삭제되었습니다.");
            }
        }

        return "redirect:/location/" + id;
    }
}
