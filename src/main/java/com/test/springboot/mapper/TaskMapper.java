package com.test.springboot.mapper;

import com.test.springboot.dto.TaskDto;
import com.test.springboot.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    TaskDto toResponseDto(Task task);

    @Mapping(target = "employee", ignore = true)
    Task toEntity(TaskDto dto);

}
