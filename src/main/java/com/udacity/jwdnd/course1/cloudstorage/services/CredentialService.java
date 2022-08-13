package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;

@Service
public class CredentialService {

	@Autowired
	private CredentialMapper credentialMapper;
	@Autowired
	private EncryptionService encryptionService;
	@Autowired
	private UserService userService;

	public int createUpdate(Credential credential, Authentication authentication) {
		Credential credentialSelectById = credentialMapper.selectById(credential.getCredentialId());
		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[16];
		secureRandom.nextBytes(key);
		String credentialKey = Base64.getEncoder().encodeToString(key);
		Credential credentialToSave = null;
		String username = authentication.getName();
		User user = userService.getUser(username);
		if (credentialSelectById == null) {
			String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credentialKey);
			credentialToSave = new Credential();
			credentialToSave.setCredentialKey(credentialKey);
			credentialToSave.setPassword(encryptedPassword);
			credentialToSave.setUrl(credential.getUrl());
			credentialToSave.setUserId(user.getUserId());
			credentialToSave.setUsername(credential.getUsername());
			return credentialMapper.insert(credentialToSave);
		} else {
			String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credentialKey);
			credentialToSave = new Credential();
			credentialToSave.setCredentialKey(credentialKey);
			credentialToSave.setPassword(encryptedPassword);
			credentialToSave.setUrl(credential.getUrl());
			credentialToSave.setUserId(user.getUserId());
			credentialToSave.setUsername(credential.getUsername());
			credentialToSave.setCredentialId(credential.getCredentialId());
			return credentialMapper.update(credentialToSave);
		}
	}

	public int delete(Integer credentialId) {
		return credentialMapper.delete(credentialId);
	}

	public List<Credential> getAll(Integer userId) {
		List<Credential> credentials = credentialMapper.selectAll(userId);
		String decryptedPassword;
		for (Credential credential : credentials) {
			decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getCredentialKey());
			credential.setDecryptedPassword(decryptedPassword);
		}
		return credentials;
	}

	public Credential getById(Integer credentialId) {
		return credentialMapper.selectById(credentialId);
	}
}