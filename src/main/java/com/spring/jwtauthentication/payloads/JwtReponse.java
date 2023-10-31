package com.spring.jwtauthentication.payloads;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class JwtReponse {

	private int success;
	
	private String message;
	
	private Map<String, Object> data;
	
	private String token;

	private String refreshToken; 

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
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
	
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	
	public JwtReponse(int success, String message, Map<String, Object> data, String token, String refreshToken) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
		this.token = token;
		this.refreshToken = refreshToken;
	}

	public JwtReponse() {
		super();
		// TODO Auto-generated constructor stub
	}	
}
