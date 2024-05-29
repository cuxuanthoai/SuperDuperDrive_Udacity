package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {

    @Delete("DELETE FROM FILES WHERE id = #{id} AND userid = #{userId}")
    void remove(File file);

    @Select("SELECT id, name, content_type, size, data, userid FROM FILES WHERE id = #{id} AND userid = #{userId}")
    @Results({
        @Result(property = "contentType", column = "content_type")
    })
    File fetch(File file);

    @Select("SELECT id, name FROM FILES WHERE userid = #{userId} AND name = #{name}")
    File locate(File file);

    @Select("SELECT id, name FROM FILES WHERE userid = #{UID}")
    List<File> fetchAllByUserId(String UID);

    @Insert("INSERT INTO FILES (id, name, content_type, size, data, userid) " +
                        "VALUES(#{id}, #{name}, #{contentType}, #{size}, #{data}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(File file);

}

