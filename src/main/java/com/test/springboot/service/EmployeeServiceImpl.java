package com.test.springboot.service;

import java.util.Optional;

import com.test.springboot.dto.EmployeeRequestDto;
import com.test.springboot.dto.EmployeeResponseDto;
import com.test.springboot.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.springboot.dao.EmployeeRepository;
import com.test.springboot.entity.Employee;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeMapper employeeMapper;
	private EmployeeRepository employeeRepository;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeMapper theEmployeeMapper, EmployeeRepository theEmployeeRepository) {
		employeeRepository = theEmployeeRepository;
		employeeMapper = theEmployeeMapper;
	}
	
	@Override
	public Page<EmployeeResponseDto> findAll(Pageable pageable) {
		return employeeRepository
				.findAll(pageable)
				.map(employeeMapper::toResponseDto);
	}

	@Override
	public EmployeeResponseDto findById(int id) {
		Optional<Employee> result = employeeRepository.findWithTasksById(id);
		Employee employee = result.orElseThrow(() -> new IllegalArgumentException("Did not find employee id - " + id));
		return employeeMapper.toResponseDto(employee);
	}

	@Override
	public EmployeeResponseDto findByEmail(String email) {
		Optional<Employee> employee = employeeRepository.findWithTasksByEmail(email);
		if (employee.isEmpty()) {
			throw new RuntimeException("Did not find employee with email - " + email);
		}
		return employeeMapper.toResponseDto(employee.orElse(null));
	}

	@Transactional
	@Override
	public void save(EmployeeRequestDto employee) {
		Employee employeeEntity = employeeMapper.toEntity(employee);
		employeeRepository.save(employeeEntity);
	}

	@Transactional
	@Override
	public void deleteById(int theId) {
		employeeRepository.deleteById(theId);
	}

}
