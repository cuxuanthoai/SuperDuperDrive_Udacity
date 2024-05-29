package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CredentialMapper {

    @Delete("DELETE FROM CREDENTIALS WHERE id = #{id} AND userid = #{userId}")
    void remove(Credential credential);

    @Select("SELECT id, url, password, username, userid FROM CREDENTIALS WHERE userid = #{UID} ORDER BY id DESC")
    List<Credential> fetchAllByUserId(String UID);

    @Select("SELECT id, key, url, password, username, userid FROM CREDENTIALS WHERE id = #{id} AND userid = #{userId}")
    Credential retrieve(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, password=#{password} WHERE id =#{id}")
    void modify(Credential credential);

    @Insert("INSERT INTO CREDENTIALS (id, url, key, username, password, userid) VALUES(#{id}, #{url}, #{key}, #{username}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(Credential credential);

}
