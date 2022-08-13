package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;

@Service
public class AuthenticationService implements AuthenticationProvider {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private HashService hashService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		User user = userMapper.SelectByUsername(username);
		if (user != null) {
			String encodedSalt = user.getSalt();
			String hashedPassword = hashService.getHashedValue(password, encodedSalt);
			if (user.getPassword().equals(hashedPassword)) {
				return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}