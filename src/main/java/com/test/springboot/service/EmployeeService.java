package com.test.springboot.service;

import com.test.springboot.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

	Page<Employee> findAll(Pageable pageable);
	
	Employee findById(int theId);

	Employee findByEmail(String email);

	void save(Employee theEmployee);

	void deleteById(int theId);
}
