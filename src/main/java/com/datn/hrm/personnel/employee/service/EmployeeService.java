package com.datn.hrm.personnel.employee.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.personnel.employee.dto.Employee;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.personnel.employee.mapper.EmployeeMapper;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IService<Employee> {

    @Override
    public Page<Employee> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByNameContainingIgnoreCase(search, pageable)
        );
    }

    @Override
    public Employee getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public Employee postObject(Employee dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public Employee putObject(long id, Employee dto) {

        EmployeeEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto))
        );
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    EmployeeRepository repository;

    @Autowired
    EmployeeMapper mapper;
}
