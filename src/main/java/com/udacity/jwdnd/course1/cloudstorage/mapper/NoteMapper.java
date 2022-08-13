package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;

@Mapper
public interface NoteMapper {
	@Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "noteId")
	int insert(Note note);

	@Update("Update NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription}"
			+ " WHERE noteId = #{noteId}")
	int update(Note note);

	@Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
	void delete(int noteId);

	@Select("SELECT * FROM NOTES")
	List<Note> selectAll(Integer userId);

	@Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
	Note selectById(Integer noteId);
}
