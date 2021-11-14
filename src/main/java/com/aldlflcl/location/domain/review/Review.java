package com.aldlflcl.location.domain.review;

import com.aldlflcl.location.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class Review {

    private int reviewId;

    private User user;

    @NotEmpty(message = "내용을 입력해주세요.")
    @Length(max = 100, message = "100자 이내로 입력해주세요.")
    private String content;

    private int star;
}
