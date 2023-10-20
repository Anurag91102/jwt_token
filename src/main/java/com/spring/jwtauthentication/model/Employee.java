package com.spring.jwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "email"), @UniqueConstraint(columnNames = "phone")})
public class Employee 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 25)
	@NotEmpty(message = "First name cannot be empty")
	@Size(min = 2,max = 10,message = "First name should be between 2 - 10 characters")
	@Pattern(regexp = "^[A-Za-z]+$",message = "First name can only contains alphabets")
	private String fname;
	
	@NotEmpty(message = "Last name cannot be empty")
	@Size(min = 3,max = 10,message = "Last name should be between 3 - 10 characters")
	@Pattern(regexp = "^[A-Za-z]+$",message = "Last name can only contains alphabets")
	@Column(length = 25)
	private String lname;
	
	@NotEmpty(message = "Phone cannot be empty")
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
	@Column(length = 15)
	private String phone;
	
	@JsonIgnore
	@Column(length = 25)
	private String role;
	
	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "Invalid email address")
	@Column(length = 45)
	private String email;
	
	@JsonIgnore
	@Column(length = 5)
	private int isVerified;
	
	@JsonIgnore
	@NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, max = 60, message = "Password must be minimum 8 characters")
    private String password;

	private String imagePath;
	
	public int getId() 
	{
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Employee(
			@NotEmpty(message = "First name cannot be empty") @Size(min = 2, max = 10, message = "First name should be between 2 - 10 characters") @Pattern(regexp = "^[A-Za-z]+$", message = "First name can only contains alphabets") String fname,
			@NotEmpty(message = "Last name cannot be empty") @Size(min = 3, max = 10, message = "Last name should be between 3 - 10 characters") @Pattern(regexp = "^[A-Za-z]+$", message = "Last name can only contains alphabets") String lname,
			@NotEmpty(message = "Phone cannot be empty") @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits") String phone,
			String role,
			@NotEmpty(message = "Email cannot be empty") @Email(message = "Invalid email address") String email,
			int isVerified,
			@NotEmpty(message = "Password cannot be empty") @Size(min = 6, max = 60, message = "Password must be minimum 8 characters") String password,
			String imagePath) 
	{
		super();
		this.fname = fname;
		this.lname = lname;
		this.phone = phone;
		this.role = role;
		this.email = email;
		this.isVerified = isVerified;
		this.password = password;
		this.imagePath = imagePath;
	}

	public int getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(int isVerified) {
		this.isVerified = isVerified;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Employee()
	{
		super();
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", fname=" + fname + ", lname=" + lname + ", phone=" + phone + ", role=" + role
				+ ", email=" + email + ", isVerified=" + isVerified + ", password=" + password + ", imagePath="
				+ imagePath + "]";
	}
	
	
	
}
