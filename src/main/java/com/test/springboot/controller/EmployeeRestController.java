package com.test.springboot.controller;

import com.test.springboot.dto.EmployeeRequestDto;
import com.test.springboot.dto.EmployeeResponseDto;
import com.test.springboot.dto.TaskDto;
import com.test.springboot.service.EmployeeService;
import com.test.springboot.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    private EmployeeService employeeService;
    private TaskService taskService;

    public EmployeeRestController(EmployeeService theEmployeeService, TaskService theTaskService) {
        employeeService = theEmployeeService;
        taskService = theTaskService;
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable int id) {
        EmployeeResponseDto employee = employeeService.findById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/search/{email}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeByEmail(@PathVariable String email) {
        EmployeeResponseDto employee = employeeService.findByEmail(email);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employee);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<EmployeeResponseDto>> getEmployees(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                  @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(employeeService.findAll(pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void addEmployee(@Valid @RequestBody EmployeeRequestDto employee) {
        employeeService.save(employee);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "{employeeId}/tasks", produces = "application/json")
    public ResponseEntity<List<TaskDto>> getTasksByEmployee(@PathVariable int employeeId) {
        return ResponseEntity.ok(taskService.findByEmployeeId(employeeId));
    }

    @PostMapping("{employeeId}/tasks")
    public ResponseEntity<Void> addTask(@PathVariable int employeeId, @RequestBody TaskDto taskDto) {
        taskService.addTaskToEmployee(employeeId , taskDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{employeeId}/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable int employeeId, @PathVariable int taskId) {
        taskService.removeEmployeeTaskById(employeeId, taskId);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/check-session")
//    public ResponseEntity<?> checkSession(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            return ResponseEntity.ok("No session found.");
//        }
//
//        Object securityContext = session.getAttribute("SPRING_SECURITY_CONTEXT");
//        if (securityContext != null) {
//            return ResponseEntity.ok("Session exists and contains security context.");
//        } else {
//            return ResponseEntity.ok("Session exists, but no security context.");
//        }
//    }

}
