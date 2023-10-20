package com.spring.jwtauthentication.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmployeeDTO 
{
	@NotEmpty(message = "First name cannot be empty")
	@Size(min = 2,max = 10,message = "First name should be between 2 - 10 characters")
	@Pattern(regexp = "^[A-Za-z]+$",message = "First name can only contains alphabets")
	private String fname;
	
	@NotEmpty(message = "Last name cannot be empty")
	@Size(min = 3,max = 10,message = "Last name should be between 3 - 10 characters")
	@Pattern(regexp = "^[A-Za-z]+$",message = "Last name can only contains alphabets")
	private String lname;
	
	
	private MultipartFile profileimage;
	
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

	public MultipartFile getProfileimage() {
		return profileimage;
	}

	public void setProfileimage(MultipartFile profileimage) {
		this.profileimage = profileimage;
	}

	

	
	
}
