package com.aldlflcl.location.domain.user;

import com.aldlflcl.location.domain.location.Location;
import com.aldlflcl.location.domain.review.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {

    private int userId;

    private String email;

    private String password;

    private String name;

    private List<Location> locations;

    private List<Review> reviews;
}
