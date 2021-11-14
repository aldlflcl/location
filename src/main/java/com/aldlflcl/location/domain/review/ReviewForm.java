package com.aldlflcl.location.domain.review;

import com.aldlflcl.location.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewForm {

    private int reviewId;
    private User user;
    private int star;
    private String content;
}
