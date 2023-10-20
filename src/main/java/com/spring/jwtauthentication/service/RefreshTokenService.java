package com.spring.jwtauthentication.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
		System.out.println(existRefreshToken);
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
	
	public RefreshToken verifyRefreshToken(String refreshToken)
	{
		Optional<RefreshToken> refreshTok = Optional.ofNullable(refreshTokenRepo.findByRefreshToken(refreshToken).orElseThrow(()-> new RuntimeException("Given Token do not exists")));
		if(refreshTok.get().getExpiry().compareTo(new Date(System.currentTimeMillis()))<0)
		{
			refreshTokenRepo.delete(refreshTok.get());
			throw new RuntimeException("Refresh Token Expired.");
		}
		else
		{
			return refreshTok.get();
		}
	}
}
