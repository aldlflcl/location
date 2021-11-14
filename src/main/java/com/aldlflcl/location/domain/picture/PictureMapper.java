package com.aldlflcl.location.domain.picture;

import com.aldlflcl.location.domain.location.Location;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PictureMapper {

    @Insert("INSERT INTO picture(location_id, address) VALUES(#{location.locationId}, #{picture.address})")
    @SelectKey(statement = "SELECT picture_seq.currval from dual", keyProperty = "picture.pictureId", before = false, resultType = int.class)
    int insertPicture(@Param("location") Location location, @Param("picture") Picture picture);

    @Delete("DELETE FROM picture WHERE picture_id = #{picture_id}")
    int deletePicture(@Param("picture_id") int pictureId);

    @Delete("DELETE FROM picture WHERE location_id = #{location.locationId}")
    int deleteByLocationId(@Param("location") Location location);

    @Select("SELECT * FROM picture WHERE picture_id = #{id}")
    @Result(property = "pictureId", column = "picture_id")
    Picture getById(@Param("id") int id);
}
