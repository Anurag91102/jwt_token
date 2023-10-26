package com.spring.jwtauthentication.service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.jwtauthentication.exception.TokenGetExpired;
import com.spring.jwtauthentication.model.Employee;
import com.spring.jwtauthentication.model.RefreshToken;
import com.spring.jwtauthentication.repo.EmployeeRepo;
import com.spring.jwtauthentication.repo.RefreshTokenRepo;

@Service
public class RefreshTokenService 
{
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private RefreshTokenRepo refreshTokenRepo;

	public RefreshToken createRefreshToken(String username)
	{
		Employee employee = employeeRepo.findByEmail(username);
		RefreshToken existRefreshToken = employee.getRefreshToken();
//		System.out.println(existRefreshToken);
		System.out.println();
		if(existRefreshToken == null)
		{
			RefreshToken refreshToken = new RefreshToken();
			refreshToken.setRefreshToken(UUID.randomUUID().toString());
			refreshToken.setExpiry(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000));
			refreshToken.setEmployee(employee);
			employee.setRefreshToken(refreshToken);
			refreshTokenRepo.save(refreshToken);
			return refreshToken;
		}
		else 
		{
			existRefreshToken.setExpiry(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000));
			employee.setRefreshToken(existRefreshToken);
			refreshTokenRepo.save(existRefreshToken);
			return existRefreshToken;
		}
	}
	
	public RefreshToken verifyRefreshToken(String refreshToken) throws TokenGetExpired
	{
		RefreshToken refreshTok = refreshTokenRepo.findByRefreshToken(refreshToken).orElseThrow(()-> new NoSuchElementException("No Such Refresh Token found"));
		if(refreshTok.getExpiry().compareTo(new Date(System.currentTimeMillis()))<0)
		{  
			refreshTokenRepo.delete(refreshTok);
			throw new TokenGetExpired("Refresh Token Expired.");
		}
		else
		{
			return refreshTok;
		}
	}

}
