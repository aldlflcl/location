package com.aldlflcl.location.domain.review;

import com.aldlflcl.location.domain.user.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewRepository {

    private final ReviewMapper reviewMapper;

    @Transactional
    public Review createReview(LoginForm user, int locationId, Review review) {
        reviewMapper.createReview(user, locationId, review);
        return review;
    }

    @Transactional
    public int deleteReview(int commentId) {
        return reviewMapper.deleteReview(commentId);
    }
}
