package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired
	private FileService fileService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) throws IOException {
		String username = authentication.getName();
		User user = userService.getUser(username);
		if(user != null) {
			fileService.saveFile(file, user.getUserId());
		}
		return "redirect:/home";
	}
	
	@GetMapping("/download/{fileId}")
	public ResponseEntity<byte[]> getFile(@PathVariable Integer fileId) {
		File file = fileService.viewFile(fileId);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
				.body(file.getFileData());
	}
	
	@GetMapping("/delete/{fileId}")
	public String delete(@PathVariable Integer fileId) {
		fileService.deleteFile(fileId);
		return "redirect:/home";
	}
	
}
