package com.test.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.springboot.dao.EmployeeRepository;
import com.test.springboot.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository) {
		employeeRepository = theEmployeeRepository;
	}
	
	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAllByOrderByLastNameAsc();
	}

	@Override
	public Employee findById(int theId) {
		Optional<Employee> result = employeeRepository.findById(theId);
		Employee employee = null;
		if (result.isPresent()) {
			employee = result.get();
		}
		else {
			throw new RuntimeException("Did not find employee id - " + theId);
		}
		return employee;
	}

	@Override
	public Employee findByEmail(String email) {
		Optional<Employee> employee = employeeRepository.findByEmail(email);
		if (employee.isEmpty()) {
			throw new RuntimeException("Did not find employee with email - " + email);
		}
		return employee.orElse(null);
	}

	@Override
	public void save(Employee theEmployee) {
		employeeRepository.save(theEmployee);
	}

	@Override
	public void deleteById(int theId) {
		employeeRepository.deleteById(theId);
	}

}
