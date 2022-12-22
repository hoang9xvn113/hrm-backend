package com.datn.hrm.organization.department.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.common.utils.DefaultValue;
import com.datn.hrm.organization.department.dto.Department;
import com.datn.hrm.organization.department.entity.DepartmentEntity;
import com.datn.hrm.organization.department.mapper.DepartmentMapper;
import com.datn.hrm.organization.department.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService implements IService<Department> {

    @Override
    public Page<Department> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByParentIdAndNameContainingIgnoreCase(DefaultValue.LONG, search, pageable)
        );
    }

    @Override
    public Department getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public Department postObject( Department dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public Department putObject(long id, Department dto) {

        DepartmentEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto))
        );
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    DepartmentRepository repository;

    @Autowired
    DepartmentMapper mapper;
}
