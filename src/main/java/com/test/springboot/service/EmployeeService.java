package com.test.springboot.service;

import com.test.springboot.dto.EmployeeRequestDto;
import com.test.springboot.dto.EmployeeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

	Page<EmployeeResponseDto> findAll(Pageable pageable);
	
	EmployeeResponseDto findById(int theId);

	EmployeeResponseDto findByEmail(String email);

	void save(EmployeeRequestDto theEmployee);

	void deleteById(int theId);
}
