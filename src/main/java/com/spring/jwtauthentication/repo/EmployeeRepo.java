package com.spring.jwtauthentication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.jwtauthentication.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer>
{
	public Employee findByEmail(String email);
}
