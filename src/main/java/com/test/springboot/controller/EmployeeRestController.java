package com.test.springboot.controller;

import com.test.springboot.entity.Employee;
import com.test.springboot.service.EmployeeService;
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
    public ResponseEntity<List<Employee>> getEmployees(@RequestParam(name = "page", defaultValue = "0") int page,
                                                       @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(employeeService.findAll(pageable).toList());
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
