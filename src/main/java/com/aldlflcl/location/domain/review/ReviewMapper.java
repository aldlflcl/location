package com.aldlflcl.location.domain.review;

import com.aldlflcl.location.domain.location.Location;
import com.aldlflcl.location.domain.user.LoginForm;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ReviewMapper {

    @Select("SELECT * FROM comments " +
            "WHERE id = #{id}")
    Review getById(@Param("id") int id);

    @Insert("INSERT INTO comments(user_id, location_id, content, star) VALUES(#{user.userId}, #{locationId}, #{review.content}, #{review.star})")
    @SelectKey(keyProperty = "review.reviewId", resultType = Integer.class, before = false, statement = "SELECT comments_seq.currval FROM DUAL")
    int createReview(@Param("user")LoginForm user, @Param("locationId")int LocationId, @Param("review") Review review);

    @Delete("DELETE FROM comments where comment_id = #{commentId}")
    int deleteReview(@Param("commentId") int commentId);

    @Delete("DELETE FROM comments WHERE location_id = #{location.locationId}")
    int deleteByLocationId(@Param("location")Location location);
}
