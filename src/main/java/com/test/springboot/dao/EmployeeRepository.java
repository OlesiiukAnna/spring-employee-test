package com.test.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.springboot.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAllByOrderByLastNameAsc();

    Optional<Employee> findByEmail(String email);
}
