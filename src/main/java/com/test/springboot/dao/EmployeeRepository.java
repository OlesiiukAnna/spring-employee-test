package com.test.springboot.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.test.springboot.entity.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @EntityGraph(attributePaths = {"tasks"})
    Optional<Employee> findWithTasksById(int id);
    @EntityGraph(attributePaths = {"tasks"})
    Page<Employee> findAllByOrderByLastNameAsc(Pageable pageable);
    @EntityGraph(attributePaths = {"tasks"})
    Optional<Employee> findWithTasksByEmail(String email);

}
