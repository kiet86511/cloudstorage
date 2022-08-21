package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;

@Mapper
public interface CredentialMapper {
	@Insert("INSERT INTO CREDENTIALS(url, username, credentialKey, password, userId) "
			+ "VALUES (#{url}, #{username}, #{credentialKey}, #{password}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "credentialId")
	int insert(Credential credential);
	
	@Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
	List<Credential> selectAll(Integer userId);
	
	@Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
	Credential selectById(Integer credentialId);
	
	@Update("UPDATE CREDENTIALS SET url = #{url}, password = #{password}, username = #{username}, credentialKey = #{credentialKey} WHERE credentialid = #{credentialId}")
	int update(Credential credential);
	
	@Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
	int delete(Integer credentialId);
}
