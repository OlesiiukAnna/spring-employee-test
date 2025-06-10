package com.test.springboot.mapper;

import com.test.springboot.dto.EmployeeRequestDto;
import com.test.springboot.dto.EmployeeResponseDto;
import com.test.springboot.entity.Employee;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { TaskMapper.class })
public interface EmployeeMapper {

    @Mapping(target = "tasks", source = "tasks")
    Employee toEntity(EmployeeRequestDto dto);

    @Mapping(target = "tasks", source = "tasks")
    EmployeeResponseDto toResponseDto(Employee entity);

    @AfterMapping
    default void linkTasks(@MappingTarget Employee employee, EmployeeRequestDto dto) {
        if (employee.getTasks() != null) {
            employee.getTasks().forEach(task -> task.setEmployee(employee));
        }
    }

}
