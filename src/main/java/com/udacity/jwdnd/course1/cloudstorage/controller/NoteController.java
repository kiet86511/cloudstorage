package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/note")
public class NoteController {
	
	private NoteService noteService;
	
	private UserService userService;
	
	public NoteController(NoteService noteService, UserService userService) {
		this.noteService = noteService;
		this.userService = userService;
	}
	
	@PostMapping("/create")
	public String create(Note note, Authentication authentication) {
		String username = authentication.getName();
		User user = userService.getUser(username);
		if(user != null) {
			noteService.create(note, authentication);
		}
		return "redirect:/home";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		noteService.delete(id);
		return "redirect:/home";
	}
}
