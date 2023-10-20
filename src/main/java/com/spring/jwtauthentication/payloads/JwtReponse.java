package com.spring.jwtauthentication.payloads;

import org.springframework.stereotype.Component;

@Component
public class JwtReponse {

	private String token;

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

	public JwtReponse(String token, String email, String fname, String lname, String phone) {
		super();
		this.token = token;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.phone = phone;
	}

	public JwtReponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
