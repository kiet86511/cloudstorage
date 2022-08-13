package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private HashService hashService;
	
	public boolean isUsernameAvailable(String username) {
		return userMapper.SelectByUsername(username) == null;
	}
	
	public int createUser(User user) {
		SecureRandom secureRandom = new SecureRandom();
		byte[] salt = new byte[16];
		secureRandom.nextBytes(salt);
		String encodedSalt = Base64.getEncoder().encodeToString(salt);
		String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
		return userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()) );
	}
	
	public User getUser(String username) {
		return userMapper.SelectByUsername(username);
	}
}
