package com.test.springboot.dao;

import com.test.springboot.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByEmployeeId(int employeeId);

}
