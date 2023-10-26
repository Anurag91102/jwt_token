package com.spring.jwtauthentication.payloads;

import org.springframework.stereotype.Component;

@Component
public class JwtReponse {

	private int success;
	
	private String message;
	
	private String token;

	private String refreshToken; 
	
	private String email;
	
	private String fname;
	
	private String lname;
	
	private String phone;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

		

	@Override
	public String toString() {
		return "JwtReponse [success=" + success + ", message=" + message + ", token=" + token + ", refreshToken="
				+ refreshToken + ", email=" + email + ", fname=" + fname + ", lname=" + lname + ", phone=" + phone
				+ "]";
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JwtReponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtReponse(int success, String message, String token, String refreshToken, String email, String fname,
			String lname, String phone) {
		super();
		this.success = success;
		this.message = message;
		this.token = token;
		this.refreshToken = refreshToken;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.phone = phone;
	}


	
	
	
	
}
