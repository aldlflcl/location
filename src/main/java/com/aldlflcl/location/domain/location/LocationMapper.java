package com.aldlflcl.location.domain.location;

import com.aldlflcl.location.domain.picture.Picture;
import com.aldlflcl.location.domain.review.Review;
import com.aldlflcl.location.domain.review.ReviewForm;
import com.aldlflcl.location.domain.user.LoginForm;
import com.aldlflcl.location.domain.user.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LocationMapper {

    @Insert("insert into location(title, content, addr_x, addr_y, user_id) values(#{location.title}, #{location.content}, #{location.addrX}, #{location.addrY}, #{user.userId})")
    @SelectKey(statement = "SELECT location_seq.currval FROM dual", keyProperty = "location.locationId", resultType = int.class, before = false)
    int insertLocation(@Param("location") Location location, @Param("user") LoginForm loginForm);

    @Select("select * from location")
    @ResultMap("LocationMap")
    List<Location> locationList();


    @Select("SELECT * FROM location where location_id = #{id}")
    @Results(id = "LocationMap", value = {
            @Result(property = "addrX", column = "ADDR_X"),
            @Result(property = "addrY", column = "ADDR_Y"),
            @Result(property = "reviews", column = "location_id", many = @Many(select = "reviewGetById")),
            @Result(property = "pictures", column = "location_id", many = @Many(select = "pictureGetById")),
            @Result(property = "user", column = "user_id", one = @One(select = "userGetById")),
            @Result(property = "locationId", column = "location_id")
    })
    Location getLocation(@Param("id") int id);

    @Select("SELECT location_id, ADDR_X, ADDR_Y, title FROM location")
    @Results({
            @Result(property = "locationId", column = "location_id"),
            @Result(property = "addrX", column = "ADDR_X"),
            @Result(property = "addrY", column = "ADDR_Y")
    })
    List<LocationGeoData> locationGeoDataList();

    @Select("SELECT * FROM comments " +
            "WHERE location_id = #{id}")
    @Results({
            @Result(property = "user", column = "user_id", one = @One(select = "userGetById")),
            @Result(property = "reviewId", column = "comment_id")
    })
    ReviewForm reviewGetById(@Param("id") int id);

    @Select("SELECT * FROM picture " +
            "WHERE location_id = #{id}")
    @Result(property = "pictureId", column = "picture_id")
    Picture pictureGetById(@Param("id") int id);

    @Select("SELECT * FROM users " +
            "WHERE user_id = #{id}")
    @Results({
            @Result(property = "userId", column = "user_id")
    })
    User userGetById(@Param("id") int id);

    @Update("UPDATE location SET title = #{location.title}, content = #{location.content}, addr_x = #{location.addrX}, addr_y = #{location.addrY} " +
            "WHERE location_id = #{location.locationId}")
    int updateLocation(@Param("location") Location location);

    @Delete("DELETE FROM location where location_id = #{location.locationId}")
    int deleteLocation(@Param("location") Location location);
}
