package com.udacity.jwdnd.course1.cloudstorage.entity;

public class Credential {
	private Integer credentialId;
	private String url;
	private String username;
	private String credentialKey;
	private String password;
	private Integer userId;
	private String decryptedPassword;

	public Credential() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Credential(Integer credentialId, String url, String username, String credentialKey, String password,
			Integer userId, String decryptedPassword) {
		super();
		this.credentialId = credentialId;
		this.url = url;
		this.username = username;
		this.credentialKey = credentialKey;
		this.password = password;
		this.userId = userId;
		this.decryptedPassword = decryptedPassword;
	}

	public Integer getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(Integer credentialId) {
		this.credentialId = credentialId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCredentialKey() {
		return credentialKey;
	}

	public void setCredentialKey(String credentialKey) {
		this.credentialKey = credentialKey;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDecryptedPassword() {
		return decryptedPassword;
	}

	public void setDecryptedPassword(String decryptedPassword) {
		this.decryptedPassword = decryptedPassword;
	}

}
