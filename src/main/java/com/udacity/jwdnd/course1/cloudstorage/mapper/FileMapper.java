package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;

@Mapper
public interface FileMapper {
	@Insert("INSERT INTO FILES(fileName, contentType, fileSize, userId, fileData) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	int insert(File file);
	
	@Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
	void deleteById(Integer fileId);
	
	@Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
	File selectById(Integer fileId);
	
	@Select("SELECT * FROM FILES WHERE filename = #{filename}")
	File selectByFileName(String filename, Integer userId);
	
	@Select("SELECT * FROM FILES where userId = #{userId}")
	List<File> selectAll(Integer userId);
}
