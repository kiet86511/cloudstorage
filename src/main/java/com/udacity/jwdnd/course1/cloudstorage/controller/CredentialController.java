package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

@Controller
@RequestMapping("/credential")
public class CredentialController {
	@Autowired
	private CredentialService credentialService;

	@PostMapping("/create")
	public String create(Credential credential, Authentication authentication, RedirectAttributes ra) {
		try {
			credentialService.createUpdate(credential, authentication);
			ra.addFlashAttribute("uploadSuccess", true);
		} catch(Exception e) {
			ra.addFlashAttribute("uploadFail", true);
		}
		
		return "redirect:/result";
	}

	@GetMapping("/delete/{credentialId}")
	public String delete(@PathVariable Integer credentialId, RedirectAttributes ra) {
		try {
			credentialService.delete(credentialId);
			ra.addFlashAttribute("uploadSuccess", true);
		} catch(Exception e) {
			ra.addFlashAttribute("uploadError", true);
		}
		return "redirect:/result";
	}
}
