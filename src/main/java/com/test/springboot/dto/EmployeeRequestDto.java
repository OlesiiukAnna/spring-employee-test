package com.test.springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class EmployeeRequestDto {

    @NotBlank(message = "Employee's first name should not be blank")
    private String firstName;
    @NotBlank(message = "Employee's last name should not be blank")
    private String lastName;
    @Email(message = "Please provide a valid email address")
    private String email;
    @Size(min = 5, max = 20,
    message = "Password must be between 5 and 20 symbols")
    private String password;
    private List<TaskDto> tasks;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }
}
