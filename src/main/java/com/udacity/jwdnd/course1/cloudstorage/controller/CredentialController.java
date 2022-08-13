package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

@Controller
@RequestMapping("/credential")
public class CredentialController {
	@Autowired
	private CredentialService credentialService;

	@PostMapping("/create")
	public String create(Credential credential, Authentication authentication) {
		credentialService.createUpdate(credential, authentication);
		return "redirect:/home";
	}

	@GetMapping("/delete/{credentialId}")
	public String delete(@PathVariable Integer credentialId) {
		credentialService.delete(credentialId);
		return "redirect:/home";
	}
}
