package com.test.springboot.mapper;

import com.test.springboot.dto.EmployeeRequestDto;
import com.test.springboot.dto.EmployeeResponseDto;
import com.test.springboot.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequestDto dto);

    EmployeeResponseDto toResponseDto(Employee entity);

}
