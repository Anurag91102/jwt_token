package com.spring.jwtauthentication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.jwtauthentication.model.Employee;
import com.spring.jwtauthentication.repo.EmployeeRepo;

@Service
public class EmployeeService 
{
	@Autowired
	private EmployeeRepo employeeRepo;
	
	public Employee save(Employee employee)
	{
		return employeeRepo.save(employee);
	}
	
	public Employee findByEmail(String email)
	{
		return employeeRepo.findByEmail(email);
	}
	
	public Optional<Employee> findById(int id)
	{
		return employeeRepo.findById(id);
	}	
}
