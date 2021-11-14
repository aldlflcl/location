package com.aldlflcl.location.domain.user;

import com.aldlflcl.location.domain.review.Review;
import com.aldlflcl.location.domain.review.ReviewForm;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into users(name, email, password) values(#{user.name}, #{user.email}, #{user.password})")
    @SelectKey(statement = "SELECT users_seq.currval from dual", resultType = Integer.class, before = false, keyProperty = "user.userId")
    void createUser(@Param("user") RegisterForm user);

    @Select("select count(user_id) from users where email = #{user.email}")
    Integer checkEmail(@Param("user") RegisterForm user);

    @Select("select count(user_id) from users where name = #{user.name}")
    Integer checkName(@Param("user") RegisterForm user);

    @Select("select * from users where email = #{email}")
    @Results({
            @Result(property = "userId", column = "user_id")
    })
    LoginForm getByEmail(@Param("email") String email);

    @Select("select * from users where user_id = #{userId}")
    @Results({
            @Result(property = "reviews", column = "user_id", many = @Many(select = "getByUserId"))
    })
    User getById(@Param("userId") int userId);


    @Select("select * from comments where user_id = #{userId}")
    @Results({
            @Result(property = "reviewId", column = "comment_id")
    })
    Review getByUserId(@Param("user_id") int userId);
}
