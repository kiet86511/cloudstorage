package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class MainController {
	
	@Autowired
	private FileService fileService;
	@Autowired
	private UserService userService;
	@Autowired
	private NoteService noteService;
	@Autowired
	private CredentialService credentialService;
	
	@GetMapping("/home")
	public String homeView(Authentication authentication, Note note, Credential credential, Model model) {
		String username = authentication.getName();
		User user = userService.getUser(username);
		
		if(user != null) {
			model.addAttribute("fileList", fileService.getFileList(user.getUserId()));
			model.addAttribute("notes", noteService.getAll(user.getUserId()));
			model.addAttribute("credentials", credentialService.getAll(user.getUserId()));
		}
		
		return "home";
	}
	
	@GetMapping("/result")
	public String resultView() {
		return "result";
	}
	
}
