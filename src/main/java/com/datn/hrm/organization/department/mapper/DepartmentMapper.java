package com.datn.hrm.organization.department.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.common.utils.GetterUtils;
import com.datn.hrm.organization.department.dto.Department;
import com.datn.hrm.organization.department.entity.BusinessEntity;
import com.datn.hrm.organization.department.entity.DepartmentEntity;
import com.datn.hrm.organization.department.repository.BusinessRepository;
import com.datn.hrm.organization.department.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentMapper implements IMapper<Department, DepartmentEntity> {

    @Override
    public Department mapDtoFromEntity(DepartmentEntity entity) {

        Department dto = new Department();

        dto.setId(entity.getId());

        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setParentId(GetterUtils.getLong(entity.getParentId()));
        dto.setBusinessId(entity.getBusinessId());
        dto.setDescription(entity.getDescription());
        dto.setAddress(entity.getAddress());

        Optional<BusinessEntity> business = businessRepository.findById(entity.getBusinessId());
        business.ifPresent(businessEntity -> dto.setBusinessName(businessEntity.getName()));

        List<DepartmentEntity> departmentEntities = repository.findAllByParentId(entity.getId());

        if (!departmentEntities.isEmpty()) {

            dto.setChildren(departmentEntities.stream().map(this::mapDtoFromEntity).collect(Collectors.toList()));
        }

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public DepartmentEntity mapEntityFromDto(Department dto) {

        DepartmentEntity entity = new DepartmentEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public DepartmentEntity mapEntityFromDto(DepartmentEntity entity, Department dto) {

        entity.setCode(dto.getCode().trim());
        entity.setName(dto.getName().trim());
        entity.setParentId(GetterUtils.getLong(dto.getParentId()));
        entity.setBusinessId(dto.getBusinessId());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    @Override
    public Page<Department> mapDtoEntityFromEntityPage(Page<DepartmentEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }

    @Autowired
    BusinessRepository businessRepository;

    @Autowired
    DepartmentRepository repository;
}
