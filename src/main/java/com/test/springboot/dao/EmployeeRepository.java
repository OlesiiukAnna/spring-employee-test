package com.test.springboot.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.springboot.entity.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Page<Employee> findAllByOrderByLastNameAsc(Pageable pageable);

    Optional<Employee> findByEmail(String email);
}
