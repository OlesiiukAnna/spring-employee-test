package com.test.springboot.dao;

import com.test.springboot.entity.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @EntityGraph(attributePaths = {"tasks"})
    Optional<Employee> findWithTasksById(int id);
    @EntityGraph(attributePaths = {"tasks"})
    Optional<Employee> findWithTasksByEmail(String email);

}
