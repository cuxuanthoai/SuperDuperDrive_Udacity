package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NoteMapper {

    @Delete("DELETE FROM NOTES WHERE id = #{id} AND userid = #{userId}")
    void remove(Note note);

    @Select("SELECT * FROM NOTES WHERE userid = #{UID} ORDER BY id DESC")
    List<Note> fetchAllByUserId(String UID);

    @Update("UPDATE NOTES SET title=#{title}, description=#{description} WHERE id = #{id} AND userid = #{userId}")
    void modify(Note note);

    @Insert("INSERT INTO NOTES (id, title, description, userid) VALUES(#{id}, #{title}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(Note note);

}
