package com.test.springboot.service;

import java.util.List;

import com.test.springboot.entity.Employee;

public interface EmployeeService {

	List<Employee> findAll();
	
	Employee findById(int theId);

	Employee findByEmail(String email);

	void save(Employee theEmployee);

	void deleteById(int theId);
}
