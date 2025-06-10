package com.test.springboot.service;

import com.test.springboot.dao.EmployeeRepository;
import com.test.springboot.dao.TaskRepository;
import com.test.springboot.dto.TaskDto;
import com.test.springboot.entity.Employee;
import com.test.springboot.entity.Task;
import com.test.springboot.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, EmployeeRepository employeeRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDto> findByEmployeeId(int employeeId) {
        List<Task> tasks = taskRepository.findByEmployeeId(employeeId);
        return tasks.stream().map(taskMapper::toResponseDto).toList();
    }

    @Override
    public void save(TaskDto dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Task task = taskMapper.toEntity(dto);
        task.setEmployee(employee);
        taskRepository.save(task);
    }

    @Override
    public void deleteById(int taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public Page<TaskDto> findAll(Pageable pageable) {
        return taskRepository
                .findAll(pageable)
                .map(taskMapper::toResponseDto);
    }

}
