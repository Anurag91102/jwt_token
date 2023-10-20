package com.spring.jwtauthentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.jwtauthentication.model.Employee;
import com.spring.jwtauthentication.repo.EmployeeRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
	{
		Employee employee = employeeRepo.findByEmail(email);
		if(employee!=null)
		{
			return new CustomUserDetails(employee);
		}
		else
		{
			throw new UsernameNotFoundException("No such user found.");
		}
	}

}
