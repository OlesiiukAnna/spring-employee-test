package com.test.springboot.service;

import com.test.springboot.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskService {

    List<TaskDto> findByEmployeeId(int employeeId);

    void save(TaskDto taskRequestDto);

    void addTaskToEmployee(int employeeId, TaskDto taskDto);

    void remove(int taskId);

    void removeEmployeeTaskById(int employeeId, int taskId);

    Page<TaskDto> findAll(Pageable pageable);

}
