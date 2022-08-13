package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;

@Service
public class NoteService {
	@Autowired
	private NoteMapper noteMapper;
	@Autowired
	private UserService userService;
	
	public int create(Note note, Authentication authentication) {
		Note noteSelectedById = noteMapper.selectById(note.getNoteId());
		String userName = authentication.getName();
		User user = userService.getUser(userName);
		Note noteToSave = null;
		if(noteSelectedById == null) {
			noteToSave = new Note();
			noteToSave.setNoteDescription(note.getNoteDescription());
			noteToSave.setNoteTitle(note.getNoteTitle());
			noteToSave.setUserId(user.getUserId());
			noteToSave.setNoteId(note.getNoteId());
			return noteMapper.insert(noteToSave);
		} else {
			noteToSave = new Note();
			noteToSave.setNoteDescription(note.getNoteDescription());
			noteToSave.setNoteTitle(note.getNoteTitle());
			noteToSave.setUserId(user.getUserId());
			noteToSave.setNoteId(note.getNoteId());
			return noteMapper.update(noteToSave);
		}
	}
	
	public List<Note> getAll(Integer userId) {
		return noteMapper.selectAll(userId);
	}
	
	public void delete(Integer id) {
		noteMapper.delete(id);
	}
}
