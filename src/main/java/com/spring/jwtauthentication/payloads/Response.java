package com.spring.jwtauthentication.payloads;

import org.springframework.stereotype.Component;

@Component
public class Response 
{
	private int success;
	
	private String message;

	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Response(int success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public Response(int success, String message, Object data) 
	{
		super();
		this.success = success;
		this.message = message;
		this.data = data;
	}

	
	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
