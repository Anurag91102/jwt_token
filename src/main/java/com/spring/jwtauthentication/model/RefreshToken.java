package com.spring.jwtauthentication.model;

import java.time.Instant;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class RefreshToken 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tokenId;
	
	private String refreshToken;

	private Date expiry;
	
	@OneToOne
	private Employee employee;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public RefreshToken(String refreshToken, Date expiry, Employee employee) {
		super();
		this.refreshToken = refreshToken;
		this.expiry = expiry;
		this.employee = employee;
	}

	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	public RefreshToken() 
	{
		super();
	}

	@Override
	public String toString() 
	{
		return "RefreshToken [refreshToken=" + refreshToken + ", expiry=" + expiry + ", employee=" + employee + "]";
	}

	
}
