package com.test.springboot.controller;

import com.test.springboot.entity.Employee;
import com.test.springboot.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    private EmployeeService employeeService;

    public EmployeeRestController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/search/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        Employee theEmployee = employeeService.findByEmail(email);

        if (theEmployee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(theEmployee);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void addEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable int id) {
        employeeService.deleteById(id);
    }

}
