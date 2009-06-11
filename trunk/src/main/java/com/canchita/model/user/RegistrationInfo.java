package com.canchita.model.user;

public class RegistrationInfo {

	String username;
	String password;
	String email;
	boolean isAdmin;
	
	public RegistrationInfo(String username, String password, String email, boolean isAdmin) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.isAdmin = isAdmin;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}
	
		
}
