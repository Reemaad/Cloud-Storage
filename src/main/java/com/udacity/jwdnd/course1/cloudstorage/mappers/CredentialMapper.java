package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getUserCredentials(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Note getCredential(Integer credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES(#{url}, #{username}, #{key}, #{password} , #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username =#{username}, key =#{key}, password =#{password} WHERE credentialId =#{credentialId}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    boolean deleteCredential(Integer credentialId);
}
